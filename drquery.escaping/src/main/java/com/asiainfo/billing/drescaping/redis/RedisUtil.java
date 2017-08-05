/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asiainfo.billing.drescaping.redis;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.Jedis;

/**
 * Redis 工具类
 *
 * @author zhouquan3 May 10
 */
public class RedisUtil implements IRedis {

    private final static Log log = LogFactory.getLog(RedisUtil.class);
    private RedisTemplate<String, Object> redisTemplate;
    private long timeout = -1;  //默认是不设超时时间

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    private void expire(String key, boolean isTimeout) {
        if (timeout != -1 && isTimeout) {
            redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
        }
    }

    public void putData2Cache(String mapKey, Map<String, Object> dataMap, boolean isTimeout) {
        redisTemplate.opsForHash().putAll(mapKey, dataMap);
//        for (String key : dataMap.keySet()) {
//            redisTemplate.opsForHash().put(mapKey, key, dataMap.get(key));
//        }
        expire(mapKey, isTimeout);
    }

    public void removeByKey(String mapKey) {
        redisTemplate.delete(mapKey);
    }

    public Map<String, Object> getHashValue2Map(String mapKey) {
        HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
        return hashOperations.entries(mapKey);
    }
}
