/**
 * @Title: LocalCache.java 
 * @Package com.asiainfo.billing.drquery.cache 
 * @Description: 本地内存，数据长期有效存放
 * @date 2012-10-24 下午4:31:44 
 */
package com.asiainfo.billing.drquery.cache.local;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.asiainfo.billing.drquery.cache.CacheException;
import com.asiainfo.billing.drquery.cache.ICache;
import com.asiainfo.billing.drquery.cache.support.CacheParameters.Range;

/**
 * @author yanghui8
 *
 */
public  class LocalCache implements ICache{
	
	private static Map<String, Object> localData = new HashMap<String,Object>();

	public void putData2Cache(String key,Map<String,Object> value,boolean isTimeout) throws CacheException{
		localData.put(key, value);
	}	

	public <V> void putData2Cache(String key,V value,boolean isTimeout) throws CacheException{
		localData.put(key, value);
	}

	@SuppressWarnings("unchecked")
	public <V> V getValue(String key, String hashKey) throws CacheException{
		return (V) localData.get(key);
	}

	@SuppressWarnings("unchecked")
	public <V> V getValue(String key) throws CacheException{
		return (V) localData.get(key);
	}
	
	public void removeByKey(String key) throws CacheException{
		localData.remove(key);
	}
	
	public boolean hasKey(String key) throws CacheException{
		return localData.containsKey(key);
	}
	
	public <V> void putData2Cache(String key, List<V> value,boolean isTimeout) throws CacheException{
		localData.put(key, value);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getHashValue2Map(String key) throws CacheException{
		return (Map<String, Object>) localData.get(key);
	}
	
	@SuppressWarnings("unchecked")
	public <V> List<V> getHashValue2Collection(String key) throws CacheException{
		return (List<V>) localData.get(key);
	}

	@Override
	public <V> List<V> getValue(String key, Collection range) throws CacheException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void rename(String oldKey, String newKey) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void putData2Cache(String key, Map<String, Object> value,
			long timeout) throws CacheException {
		// TODO Auto-generated method stub
		
	}

    @Override
    public void putData2Cache(String key, Object hashKey, Object value, long timeout) throws CacheException {

    }

    @Override
    public <V> void putData2Cache(String key, V value, long timeout) throws CacheException {
        localData.put(key, value);
    }

    @Override
    public <V> void putData2CacheWithIndex(String key, List<V> value, long timeout) throws CacheException {
        localData.put(key, value);
    }


}
