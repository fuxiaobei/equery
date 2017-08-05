package com.asiainfo.billing.drquery.cache.redis;

import org.junit.Test;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Collections;

/**
 * Created by wangkai8 on 15/5/14.
 */
public class TestRedisCache {

    @Test
    public void testRedisCache() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxActive(20);
        jedisPoolConfig.setMaxWait(3000);
        jedisPoolConfig.setMaxIdle(10);
        jedisPoolConfig.setMinIdle(5);
        jedisPoolConfig.setTestOnBorrow(false);

        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName("localhost");
        factory.setPort(6379);
        factory.setPoolConfig(jedisPoolConfig);
        factory.afterPropertiesSet();

        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.afterPropertiesSet();


        redisTemplate.opsForHash().putAll("privTest", Collections.singletonMap("8001", "test1"));
        redisTemplate.opsForHash().putAll("privTest", Collections.singletonMap("8002", "test1"));
        redisTemplate.opsForHash().putAll("privTest", Collections.singletonMap("8003", "test1"));

        assert redisTemplate.opsForHash().get("privTest", "8001").equals("test1");

        //redisTemplate.opsForValue().get("$Redis_Status");
        //redisTemplate = null;

        //String val = factory.getConnection().getNativeConnection().get("\"\\xac\\xed\\x00\\x05t\\x00\\r$Redis_Status\"");
        //System.out.println(val);
        //System.out.println();

        //List<Map> list = (List)redisTemplate.opsForHash().get("PM_FREERES_DEF", "6073711");
        //List<Map> list = (List)redisTemplate.opsForHash().get("key1", "hashkey1");
        //System.out.println(list.get(0).get("FREERES_TYPE").getClass().getName());
        //System.out.println("---> " + list);

//		redisTemplate.opsForValue().set("wangkai", "male");
//		redisTemplate.opsForValue().set("leixue", "female");
//		redisTemplate.watch("wangkai_000");
//		redisTemplate.watch("leixue_000");
//		redisTemplate.multi();
//		try{
//			System.out.println("--");
//			//redisTemplate.opsForValue().set("key1", "test");
//			redisTemplate.rename("wangkai_000", "wangkai_001");
//
//			redisTemplate.rename("leixue_000", "leixue_001");
//			//int a = 1 / 0;
//			System.out.println("--");
//			redisTemplate.exec();
//		}catch(Exception e){
//			System.out.println("-------------error");
//			e.printStackTrace();
//			redisTemplate.discard();
//		}

        //redisTemplate.opsForHash().put("key1", "hashkey1", "test");
//
//		List<Map> list = new ArrayList<Map>();
//		Map recordMap = new HashMap();
//		recordMap.put("SRC_CALL_TYPE", "11");
//		recordMap.put("SRC_USER_NUMBER", "18601134210");
//		list.add(recordMap);
//
//		Map map = new HashMap();
//		map.put(1, JSONArray.fromObject(list).toString());
//		JSONObject j =  JSONObject.fromObject(map);

//		 JacksonJsonRedisSerializer<Map> json = new JacksonJsonRedisSerializer<Map>(Map.class);
//		 redisTemplate.setKeySerializer(new StringRedisSerializer());
//		 redisTemplate.setValueSerializer(new StringRedisSerializer());

        //redisTemplate.opsForHash().putAll("wangkaiTest", map);
//		 redisTemplate.opsForValue().set("wangkaiTest2", j.toString());
//
//		ValueOperations ops = redisTemplate.opsForValue();
//		String value = (String)ops.get("wangkaiTest2");
//		System.out.println(value);
//		Range range = new Range(1, 2);
//		ops.multiGet(range.getLimitKey());

        //redisTemplate.opsForHash().multiGet(null, null);

//		JedisPool pool = new JedisPool(new JedisPoolConfig(), "20.26.12.16", 6379);
//		Jedis jedis = pool.getResource();
//		jedis.hmset("wangkaiTest3", map);
//


//		int a = 1;
//		int index = 0;
//		while(a == 1){
//			if(index % 10 == 0){
//				factory = new JedisConnectionFactory();
//				factory.setHostName("20.26.12.15");
//
//				factory.setPort(6379);
//				factory.setPoolConfig(jedisPoolConfig);
//				redisTemplate.setConnectionFactory(factory);
//				factory.afterPropertiesSet();
//			}else{
//				factory = new JedisConnectionFactory();
//				factory.setHostName("20.26.12.19");
//
//				factory.setPort(6379);
//				factory.setPoolConfig(jedisPoolConfig);
//				redisTemplate.setConnectionFactory(factory);
//				factory.afterPropertiesSet();
//			}
//			index ++;
//			try {
//				System.out.println(factory.getConnection());
//				Thread.currentThread().sleep(3000);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				//e.printStackTrace();
//				System.out.println("Redis is unavbile !!!");
//			}
//		}
//		String s = "";
//		System.out.println(s.substring(1, 0));
//		String key = "0-18858601219-5011867354-571-20120724-201207-20120724-HIS-00-ocnosqlDataSource-1-20";
//		//key = "SHIELD_INFO_DTL";
//		try{
//			Range range = new Range(1, 2);
//			System.out.println("--" + redisTemplate.hasKey(key));
//			System.out.println("--"  + redisTemplate.opsForHash().get(key, 1));
//		}catch(Exception e){
//			e.printStackTrace();
//			redisTemplate.discard();
//			factory.destroy();
//		}

//		System.out.println("1: " + factory.getConnection().isPipelined());
//
//		factory = new JedisConnectionFactory();
//		factory.setHostName("20.26.12.19");
//
//		factory.setPort(6379);
//		factory.setPoolConfig(jedisPoolConfig);
//		redisTemplate.setConnectionFactory(factory);
//		//redisTemplate.afterPropertiesSet();
//		factory.afterPropertiesSet();
//		//redisTemplate.afterPropertiesSet();
//
//		System.out.println("-------------------------------------");
//		System.out.println("2: " + factory.getConnection().isPipelined());
//
//		try{
//		System.out.println(redisTemplate.opsForHash().get("SHIELD_INFO_DTL", "1369572677"));
//		}catch(Exception e){
//			e.printStackTrace();
//		}
////		redisTemplate.discard();
//		System.out.println(redisTemplate.isExposeConnection());
//		//redisTemplate.setExposeConnection(true);
//		//System.out.println(redisTemplate.isExposeConnection());
//		final String key = "SHIELD_INFO_DTL";
//
//		for(int i=0;i<1000;i++){
//			long t1 = System.currentTimeMillis();
////			redisTemplate.opsForHash().get("mm", "1369572677");
////			//System.out.println(redisTemplate.opsForHash().get("SHIELD_INFO_DTL", "1369572677"));
//
//			test(redisTemplate, key);
//			long t2 = System.currentTimeMillis();
//			if((t2 - t1) > 0){
//				System.out.println("token: " + (t2 - t1) + "ms");
//			}
//		}
    }

//	@SuppressWarnings("unchecked")
//	public static void test(RedisTemplate redisTemplate, final String key){
//		Object o = redisTemplate.execute(new RedisCallback<Object>() {
//            @Override
//            public Object doInRedis(RedisConnection connection) throws DataAccessException {
//
//                Long i = connection.dbSize();
//                return i;
//            }
//        }, false);
//		System.out.println(o);
//	}

}
