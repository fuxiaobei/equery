/**
 * @Title: RedisSwitch.java 
 * @Package com.asiainfo.billing.drquery.cache.support 
 * @Description: 切换redis
 * @date 2012-9-26 下午6:12:20 
 */
package com.asiainfo.billing.drquery.cache.support;

import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;

import com.asiainfo.billing.drquery.cache.CacheException;
import com.asiainfo.billing.drquery.cache.ICache;

/**
 * @author yanghui8
 *
 */
public class RedisSwitch implements InitializingBean{
	
	@Autowired
	private ICache redisCache;	
	@SuppressWarnings("rawtypes")
	@Autowired	
	private RedisTemplate redisTemplate;	
	private boolean isValid;
	private static final Log log = LogFactory.getLog(RedisSwitch.class);
	//private List<JedisConnectionFactory> jedisConnectionFactorys ;	
	private Map<JedisConnectionFactory,Boolean> jedisConnectionFactoryMap;
	private JedisConnectionFactory jedisConnectionFactory; //本地主机
	private int maxIdle;	//设置连接池最大值
	private int minIdle; //设置连接池最小值
	private int localMinIdle;
		
	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}
	
	public void setLocalMinIdle(int localMinIdle) {
		this.localMinIdle = localMinIdle;
	}

	public void setJedisConnectionFactory(
			JedisConnectionFactory jedisConnectionFactory) {
		this.jedisConnectionFactory = jedisConnectionFactory;
	}

	public void setJedisConnectionFactoryMap(
			Map<JedisConnectionFactory, Boolean> jedisConnectionFactoryMap) {
		this.jedisConnectionFactoryMap = jedisConnectionFactoryMap;
	}

	/**
	 * 切换redis
	 * @return
	 */
	public synchronized boolean switchRedis(){
		if(isValidRedis()){
			return true;
		}
		if(jedisConnectionFactoryMap == null )
			return false;
		boolean isSwitch = false;
		Iterator<JedisConnectionFactory> iter = jedisConnectionFactoryMap.keySet().iterator();
		while(iter.hasNext()){		
			JedisConnectionFactory factory = iter.next();
			Boolean valid = jedisConnectionFactoryMap.get(factory);
			JedisConnection  conn = null;
			if(valid == true ){
				try {
					conn = factory.getConnection();
					conn.isPipelined(); //判断redis主机是否可连
					redisTemplate.setConnectionFactory(factory);
					//Redis宕机之后服务短暂可用，切换到本机还是会成功，所以要检测本机Redis是否还能取数据，不能取数据将会抛异常
					redisTemplate.opsForValue().get("$Redis_Status");
					factory.getPoolConfig().setMaxIdle(maxIdle);
					factory.getPoolConfig().setMinIdle(minIdle);
					isSwitch = true;
					log.info("switch redis to host ["+factory.getHostName()+":"+factory.getPort()+"] success!");
					break;
				} catch (RuntimeException e) {
					jedisConnectionFactoryMap.put(factory, false);
					log.info("switch redis to host ["+factory.getHostName()+":"+factory.getPort()+"] fail!" );
					continue;
				} finally{
					if(conn != null)
						RedisConnectionUtils.releaseConnection(factory.getConnection(), factory);
				}
			}
		}
		return isSwitch;
	}
	
	/**
	 * 检查当前的redis是否可用，不可用将进行切换
	 * @return
	 */
	public void checkRedisStatus() throws CacheException{
		if(!isValidRedis()){	
			boolean result = switchRedis();
			if(result == false)
				throw new CacheException("redis hosts are unavailable !  ");
		}
	}

	
	/**
	 * 查看当前的redis是否可用
	 * @return
	 */
	public boolean isValidRedis(){
		try {
			//redisCache.getValue("forCheck"); //此处会造成无限循环
            redisTemplate.opsForValue().get("forCheck");    //用于测试redis主机是否能正查连接
			isValid = true;
			log.debug("current redis host is available !");
		} catch (RuntimeException e) {
			isValid = false;
			JedisConnectionFactory jcf  = (JedisConnectionFactory) redisTemplate.getConnectionFactory();
			jcf.getPoolConfig().setMaxIdle(0);
			jcf.getPoolConfig().setMinIdle(0);
			//当不再使用local redis host, 最小与最大本地连接数设置为0
			/*if(redisTemplate.getConnectionFactory() == jedisConnectionFactory){
				jedisConnectionFactory.getPoolConfig().setMinIdle(0);
				jedisConnectionFactory.getPoolConfig().setMaxIdle(0);
			}*/
			if(jedisConnectionFactoryMap.get(redisTemplate.getConnectionFactory()) != null)
				jedisConnectionFactoryMap.put(jcf, false);
			log.info("current redis host is unavailable ! ");
		}
		return isValid;
	}
	
	/**
	 * 
	 * @Description: 切换回local redis 的线程
	 *
	 */
	private class Switch2LocalRedis extends Thread{
		public void run() {
			do{
					try {
						sleep(60000);
					} catch (InterruptedException e) {
						throw new CacheException("Switch to local Redis interrupted exception",e);
					}
					Switch2localRedis();
					checkOtherRedis();
			}while(true);
		}
		
		/**
		 * 切换到本地的redis
		 */
		public void Switch2localRedis(){
			if(redisTemplate.getConnectionFactory() == jedisConnectionFactory)
				return;
			
			JedisConnection conn = null;
			try {
				conn = jedisConnectionFactory.getConnection();
				conn.isPipelined();  //判断redis host connection ，无论返回true||false,都可以				
				
				@SuppressWarnings("rawtypes")
				RedisTemplate redisTemp= new RedisTemplate();
				redisTemp.setConnectionFactory(jedisConnectionFactory);
				redisTemp.afterPropertiesSet();
				String curRedisStatus = (String) redisTemp.opsForValue().get("$Redis_Status");
				redisTemp = null;
				
				if(curRedisStatus !=null && "ok".equals(curRedisStatus)){				
					//回复本地最小连接数的初始值			
					jedisConnectionFactory.getPoolConfig().setMinIdle(localMinIdle);
					jedisConnectionFactory.getPoolConfig().setMaxIdle(maxIdle);
					redisTemplate.setConnectionFactory(jedisConnectionFactory);
					log.info("local redis host ["+jedisConnectionFactory.getHostName()+":"+jedisConnectionFactory.getPort()+"] is  available, so switch to local redis !");
				}
			} catch (RuntimeException e) {
				log.info("local redis host  ["+jedisConnectionFactory.getHostName()+":"+jedisConnectionFactory.getPort()+"] is  unavailable ! ");
			}finally{
				if(conn != null)
					RedisConnectionUtils.releaseConnection(conn, jedisConnectionFactory);
			}
		}
		
		/**
		 * 检查其他redis是否可用
		 */
		public void checkOtherRedis(){
			Iterator<JedisConnectionFactory> iter = jedisConnectionFactoryMap.keySet().iterator();
			while(iter.hasNext()){		
				JedisConnectionFactory factory = iter.next();
				Boolean valid = jedisConnectionFactoryMap.get(factory);				
				if(valid == false){
					JedisConnection conn = null;
					try {
						conn = factory.getConnection();
						conn.isPipelined(); //判断redis主机是否可连
						jedisConnectionFactoryMap.put(factory, true);
					} catch (RuntimeException e) {
						continue;
					}finally{
						if(conn != null)
							RedisConnectionUtils.releaseConnection(conn, jedisConnectionFactory);
					}
				}
			}
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if(!this.isValidRedis()){
			switchRedis();
		}
		
		Thread thread = new Thread(new Switch2LocalRedis());
		thread.setName("switch2LocalRedis-thread-1");
		thread.setDaemon(true);
		thread.start();
		
	}
}
