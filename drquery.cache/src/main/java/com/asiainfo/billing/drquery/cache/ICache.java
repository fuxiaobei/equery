package com.asiainfo.billing.drquery.cache;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.asiainfo.billing.drquery.cache.support.CacheParameters.Range;


/**
 * 
 * @author Rex Wong
 * @version
 */
public interface ICache {

	/**
	 * 存入缓存，存入数据为Redis map形式
	 * @param key 存入缓存数据的key
	 * @param value 存入缓存数据
	 * @param timeout 是否设置缓存超时。<code>true</code>:设置超时时间，当超过指定时间该key值下的缓存将被清除。
	 * @throws CacheException
	 */
	void putData2Cache(String key, Map<String, Object> value, long timeout) throws CacheException;

    void putData2Cache(String key, Object hashKey, Object value, long timeout) throws CacheException;
	

	<V> void putData2Cache(String key, V value, long timeout) throws CacheException;

    <V> void putData2CacheWithIndex(String key, List<V> value, long timeout) throws CacheException;

	/**
	 * @param key
	 * @param hashKey
	 * @return
	 */
	<V> V getValue(String key, String hashKey) throws CacheException;

	<V> V getValue(String key) throws CacheException;

    /**
     * 分页查询缓存中的数据
     * @return
     * @throws CacheException
     */
    <V> List<V> getValue(String key, Collection range) throws CacheException;
	
	void removeByKey(String key) throws CacheException;
	
	boolean hasKey(String key) throws CacheException;

	void rename(String oldKey, String newKey);
}
