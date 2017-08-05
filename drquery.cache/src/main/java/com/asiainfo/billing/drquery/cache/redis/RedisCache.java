package com.asiainfo.billing.drquery.cache.redis;

import com.asiainfo.billing.drquery.cache.CacheException;
import com.asiainfo.billing.drquery.cache.ICache;
import com.asiainfo.billing.drquery.cache.support.CacheParameters.Range;
import com.asiainfo.billing.drquery.cache.support.RedisSwitch;
import com.asiainfo.billing.drquery.utils.PropertiesUtil;
import com.asiainfo.billing.drquery.utils.ServiceLocator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

public class RedisCache implements ICache, InitializingBean {
	
	private static final Log log = LogFactory.getLog(RedisCache.class);
	
	private RedisTemplate<String, Object> redisTemplate;
	private BlockingQueue queue =
			new ArrayBlockingQueue(Integer.parseInt(PropertiesUtil.getProperty("drquery.service/runtime.properties","redis.map.queue.size","10000")));
    int cacheHandler = Integer.parseInt(PropertiesUtil.getProperty("drquery.service/runtime.properties","redis.cache.handler", "1"));
    int interval = Integer.parseInt(PropertiesUtil.getProperty("drquery.service/runtime.properties","redis.cache.retry.interval", "5000"));

    CopyOnWriteArrayList<String> cacheKeys = new CopyOnWriteArrayList<String>();
	
	public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public RedisTemplate<String, Object> getRedisTemplate() {
		return redisTemplate;
	}

    private RedisSwitch redisSwitch;

    public void setRedisSwitch(RedisSwitch redisSwitch) {
        this.redisSwitch = redisSwitch;
    }

    private long timeout = -1;
	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}
	

	
	
    private void handlerException(Exception e){
    	Throwable throwable = e.getCause();
		if(throwable!=null&&(throwable instanceof JedisConnectionException)){
			/*String host = PropertiesUtil.getProperty("runtime.properties","redis.host");
			String port = PropertiesUtil.getProperty("runtime.properties","redis.port");
			log.error("cant get redis connection:host["+host+"],port["+port+"]");*/
			log.error("can't get current redis connection");
			throw new JedisConnectionException(" current Redis connection failture");
			
		}
    }


    @Override
    public void putData2Cache(String key, Map<String, Object> value, long timeout) throws CacheException {
        try {
            queue.put(new CacheExecutor(redisTemplate, key, value, timeout) {
                public void executePut(RedisTemplate redisTemp, Object  key, Object value, long timeout){
                    redisTemp.opsForHash().putAll(key, (Map) value);
                    redisTemp.expire(key, timeout, TimeUnit.SECONDS);
                }
            });
        } catch (InterruptedException e) {
            throw new CacheException(e);
        }
    }

    @Override
    public void putData2Cache(String key, final Object hashKey, Object value, long timeout) throws CacheException {
        try {
            queue.put(new CacheExecutor(redisTemplate, key, value, timeout) {
                public void executePut(RedisTemplate redisTemp, Object  key, Object value, long timeout){
                    redisTemp.opsForHash().put(key, hashKey, value);
                    redisTemp.expire(key, timeout, TimeUnit.SECONDS);
                }
            });
        } catch (InterruptedException e) {
            throw new CacheException(e);
        }
    }

    @Override
    public <V> void putData2Cache(String key, V value, long timeout) throws CacheException {
        try {
            queue.put(new CacheExecutor(redisTemplate, key, value, timeout) {
                public void executePut(RedisTemplate redisTemp, Object  key, Object value, long timeout){
                    redisTemp.opsForValue().set(key, value);
                    redisTemp.expire(key, timeout, TimeUnit.SECONDS);
                }
            });
        } catch (InterruptedException e) {
            throw new CacheException(e);
        }
    }

    @Override
    public <V> void putData2CacheWithIndex(String key, List<V> value, long timeout) throws CacheException {
        Map<String, V> map = new HashMap<String, V>();
        for(int i = 0; i < value.size(); i++) {
            map.put(String.valueOf(i + 1), value.get(i));
        }
        putData2Cache(key, map, timeout);
    }

    @Override
    public <V> V getValue(String key, String hashKey) throws CacheException {
        while(true) {
            try {
                return (V) redisTemplate.opsForHash().get(key, hashKey);
            } catch (Exception e) {
                redisSwitch.checkRedisStatus();
            }
        }
    }

    @Override
    public <V> V getValue(String key) throws CacheException {
        while(true) {
            try {
                return (V) redisTemplate.opsForValue().get(key);
            } catch (Exception e) {
                redisSwitch.checkRedisStatus();
            }
        }
    }

    @Override
    public <V> List<V> getValue(String key, Collection range) throws CacheException {
        while(true) {
            try {
                return (List<V>) redisTemplate.opsForHash().multiGet(key, range);
            } catch (Exception e) {
                redisSwitch.checkRedisStatus();
            }
        }
    }

    @Override
    public void removeByKey(String key) throws CacheException {
        redisTemplate.delete(key);
    }

    @Override
    public boolean hasKey(String key) throws CacheException {
        return redisTemplate.hasKey(key);
    }

    @Override
    public void rename(String oldKey, String newKey) {
        redisTemplate.rename(oldKey, newKey);
    }


    static abstract class CacheExecutor {
        Object key;
        Object value;
        long timeout;
        RedisTemplate redisTemp;

        public CacheExecutor(RedisTemplate redisTemp, Object  key, Object value, long timeout){
            this.redisTemp = redisTemp;
            this.key = key;
            this.value = value;
            this.timeout = timeout;
        }
        public Object getKey() {
            return key;
        }
        public void setKey(Object key) {
            this.key = key;
        }
        public Object getValue() {
            return value;
        }
        public void setValue(Object value) {
            this.value = value;
        }
        public long getTimeout() {
            return timeout;
        }
        public void setTimeout(long timeout) {
            this.timeout = timeout;
        }

        public abstract void executePut(RedisTemplate redisTemp, Object key, Object value, long timeout);
    }



     class CacheThread extends Thread{


        public CacheThread(int num){
            this.setDaemon(true);
            this.setName("CacheThread-" + num);
            log.info("starting handler: " + this.getName());
        }

        public void run(){
            CacheExecutor cacheExecutor = null;
            while(true){
                try {
                    cacheExecutor = (CacheExecutor) queue.take();

                } catch (InterruptedException e) {
                    log.error("", e);
                }
                //此处是防止redis宕机造成put失败，遇到redis宕机情况需切换到其他redis
                while(true) {
                    try {
                        if (cacheExecutor != null)
                            cacheExecutor.executePut(cacheExecutor.redisTemp, cacheExecutor.key, cacheExecutor.value, cacheExecutor.timeout);
                        break;
                    } catch (Exception e) {
                        log.error("", e);

                        try {
                            redisSwitch.checkRedisStatus();
                        } catch (CacheException ex) {
                            log.error("", ex);
                            try {
                                Thread.sleep(interval);
                            } catch (Exception xx) {
                            }
                        }
                    }
                }
            }
        }
    }


    private void startThreads() {
        if(cacheHandler <= 0) {
            throw new IllegalArgumentException("cacheHandler must >= 1, but found " + cacheHandler);
        }
        for(int i = 1; i <= cacheHandler; i++) {
            CacheThread cacheThread = new CacheThread(i);
            cacheThread.start();
        }
    }

	public void afterPropertiesSet() throws Exception {
        startThreads();
	}

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        if(args.length < 3) {
            System.err.println("usage: <hostname> <port> <table> [<key>]");
        }
        RedisTemplate redisTemp = new RedisTemplate();
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(args[0]);
        jedisConnectionFactory.setPort(Integer.parseInt(args[1]));
        jedisConnectionFactory.afterPropertiesSet();
        redisTemp.setConnectionFactory(jedisConnectionFactory);
        redisTemp.afterPropertiesSet();
        if(args.length == 3) {
            Map<Object, Object> kvs = redisTemp.opsForHash().entries(args[2]);
            for(Entry<Object, Object> entry : kvs.entrySet()) {
                String key = (String) entry.getKey();
                Object val = entry.getValue();
                System.out.println(key + ": " + val);
            }
        } else {
            Object val = redisTemp.opsForHash().get(args[2], args[3]);
            System.out.println(val);
        }

    }

}
