/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asiainfo.billing.drescaping.redis;

import java.util.Map;

/**
 * Redis工具类接口
 * @author zhouquan3
 * May 10 2012
 */
public interface IRedis {
    	/**
	 * 存入缓存，存入数据为Redis map形式
	 * @param mapKey 存入缓存数据的key
	 * @param dataMap 存入缓存数据
	 * @param isTimeout 是否设置缓存超时。<code>true</code>:设置超时时间，当超过指定时间该key值下的缓存将被清除。
	 * @throws CacheException
	 */
	void putData2Cache(String mapKey,Map<String,Object> dataMap,boolean isTimeout);
        
        Map<String, Object> getHashValue2Map(String mapKey);
        
        void removeByKey(String mapKey);
}
