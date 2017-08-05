package com.asiainfo.billing.drquery.cache;


import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import com.asiainfo.billing.drquery.cache.redis.RedisCache;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;

import com.asiainfo.billing.drquery.utils.ServiceLocator;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class CacheProvider implements InitializingBean{
	private final static Log log = LogFactory.getLog(CacheProvider.class);
	private final static Map<String,Map<String,Object>> cacheData = new WeakHashMap<String,Map<String,Object>>();
	private static ICache redisCache;


    public static final String ROWKEYINFO  = "rowkeyInfo";
    public static final String COUNTINFO  = "countInfo";



	static{
		redisCache = ServiceLocator.getInstance().getService("redisCache",ICache.class);
	} 
	
	public static ICache getRedisCache() {
		return redisCache;
	}
	
	public static Object getCachingRedisMapData(String key,String hashkey) {
//        if(redisCache==null){
//        	redisCache = ServiceLocator.getInstance().getService("redisCache",ICache.class);
//        }
		Map<String,Object> redisData = cacheData.get(key);
		if(redisData == null){
            //log.info("the value is null that retrive data from cache[key:"+key+"]");
            redisData= new HashMap<String,Object>();
            cacheData.put(key, redisData);
        }
//        if(hashkey.equals("55186")){
//            log.info("------------55186 直接查redisMap-------------------" + redisData.get("55186"));
//        }
        Object data = null;
		if(hashkey == null){
			hashkey = "";
		}
		if(!redisData.containsKey(hashkey)){
    		data = redisCache.getValue(key, hashkey);
//            if(hashkey.equals("55186")){
//                log.info("------------55186 直接查redis-------------------" + data);
//            }
    		//log.info("fetch value["+data+"] from redis[key:"+key+", hashKey:"+hashkey+"]");
    		//System.out.println("key1: " + key + ",  hashKey1: " + hashkey);
    		redisData.put(hashkey, data);
    	}else{
            //System.out.println("key2: " + key + ",  hashKey2: " + hashkey);
    		data = redisData.get(hashkey);
    	}
		return data;
	}
	public void retriveCacheFromRedis(){
		log.info("clear system cache!!!");
		for(Entry<String,Map<String,Object>> entry:cacheData.entrySet()){
			entry.getValue().clear();
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		retriveCacheFromRedis();
	}

    public static String[] getEscapedValue(String key,String hashKey,String[] columns){
        String[] val = null;
		List<Map> list = (List)getCachingRedisMapData(key,hashKey);
		if(list == null){
			return val;
		}
		if(list.size() > 0){
            val = new String[columns.length];
            int index = 0;
            for(String columnName : columns){
			    val[index++] = (String)list.get(0).get(columnName);
            }
		}
		return val;
    }


	public static String getBUSI_NAMEByBUSI_ID(String columnName, String busi_id){
		String val = null;
		List<Map> list = (List)getCachingRedisMapData("DIM_BUSI",busi_id);
		if(list == null){
			return val;
		}
		if(list.size() > 0){
			val = (String)list.get(0).get(columnName);
		}
		return val;
	}

	public static String getAPP_NAMEByAPP_ID(String columnName, String app_id){
		String val = null;
		List<Map> list = (List)getCachingRedisMapData("DIM_APP",app_id);
//        if(app_id.equals("55186")){
//           log.info("------------55186转义返回值-------------------" + list==null?true:list.size());
//        }
		if(list == null){
			return val;
		}
		if(list.size() > 0){
			val = (String)list.get(0).get(columnName);
//            Map<String,Object> map = list.get(0);
//            Set<String> set =  map.keySet();
//            for(Iterator<String> it = set.iterator();it.hasNext();){
//               String key = it.next();
//               Object value =  map.get(key);
//               log.info("DIM_APP map size is "+ map.size() +" key is " + key +" value is "+ value);
//            }
		}
		return val;
	}

	public static String getBUSI_TYPE_NAMEByBUSI_TYPE_ID(String columnName, String busi_type_id){
		String val = null;
		List<Map> list = (List)getCachingRedisMapData("DIM_BUSI_TYPE",busi_type_id);
		if(list == null){
			return val;
		}
		if(list.size() > 0){
			val = (String)list.get(0).get(columnName);
//            Map<String,Object> map = list.get(0);
//            Set<String> set =  map.keySet();
//            for(Iterator<String> it = set.iterator();it.hasNext();){
//               String key = it.next();
//               Object value =  map.get(key);
//               log.info("DIM_BUSI_TYPE map size is "+ map.size() +" key is " + key +" value is "+ value);
//            }
		}
		return val;
	}

	public static String getTERM_MODEL_NAMEByTERM_MODEL_ID(String columnName, String busi_type_id){
		String val = null;
		List<Map> list = (List)getCachingRedisMapData("DIM_TERM_MODEL",busi_type_id);
		if(list == null){
			return val;
		}
		if(list.size() > 0){
			val = (String)list.get(0).get(columnName);
//            Map<String,Object> map = list.get(0);
//            Set<String> set =  map.keySet();
//            for(Iterator<String> it = set.iterator();it.hasNext();){
//               String key = it.next();
//               Object value =  map.get(key);
//               log.info("DIM_TERM_MODEL map size is "+ map.size() +" key is " + key +" value is "+ value);
//            }
		}
		return val;
	}

	public static String getSITE_NAMEBySITE_ID(String columnName, String site_id){
		String val = null;
		List<Map> list = (List)getCachingRedisMapData("DIM_WEB_SITE",site_id);
		if(list == null){
			return val;
		}
		if(list.size() > 0){
			val = (String)list.get(0).get(columnName);
		}
		return val;
	}

	public static String getACCESS_MODE_NAMEByACCESS_MODE_ID(String columnName, String accessModeId){
		String val = null;
		List<Map> list = (List)getCachingRedisMapData("DIM_ACCESS_MODE",accessModeId);
		if(list == null){
			return val;
		}
		if(list.size() > 0){
			val = (String)list.get(0).get(columnName);
		}
		return val;
	}

	public static String getLACCI_NAMEByLACCI_ID(String columnName, String locAndCi){
		String val = null;
		List<Map> list = (List)getCachingRedisMapData("DIM_LOGIC_AREA",locAndCi);
		if(list == null){
			return val;
		}
		if(list.size() > 0){
			val = (String)list.get(0).get(columnName);
		}
		return val;
	}

	public static String tabBaseType(String columnName, String CODE_TYPE, String CODE_ID){
		String val = null;
//		if(!redisCache.hasKey("QRY_BASE_TYPE_DEF")){
//			log.error("reids does not load key[QRY_BASE_TYPE_DEF].");
//			return val;
//		}
		List<Map> list = (List)getCachingRedisMapData("QRY_BASE_TYPE_DEF", CODE_TYPE + "|" + CODE_ID);
		if(list == null){
			return val;
		}
		if(list.size() > 0){
			val = (String)list.get(0).get(columnName);
		}
		return val;
	}
	
	//从redis中取  业务名的key值
	public static String getUpProductItem(String columnName, String PRODUCT_ITEM_KEY){
		String val = null;
		List<Map> list = (List)getCachingRedisMapData("UP_PRODUCT_ITEM", PRODUCT_ITEM_KEY);
		if(list == null){
			return val;
		}
		if(list.size() > 0){
			val = (String)list.get(0).get(columnName);
		}
		return val;
	}
	
	public static String tabFreeCode(String columnName, String PRODUCT_ITEM_ID){
		String val = null;
//		if(!redisCache.hasKey("PM_FREERES_DEF")){
//			log.error("reids does not load key[PM_FREERES_DEF].");
//			return val;
//		}
		List<Map> list = (List)getCachingRedisMapData("PM_FREERES_DEF", PRODUCT_ITEM_ID);
		if(list == null){
			return val;
		}
		if(list.size() > 0){
			val = (String)list.get(0).get(columnName);
		}
		return val;
	}
	
	public static boolean tabFreeCode(String PRODUCT_ITEM_ID){
		boolean val = true;
//		if(!redisCache.hasKey("PM_FREERES_DEF")){
//			log.error("reids does not load key[PM_FREERES_DEF].");
//			return false;
//		}
		List<Map> list = (List)getCachingRedisMapData("PM_FREERES_DEF", PRODUCT_ITEM_ID);
		if(list == null || list.size() == 0){
			return false;
		}
		return val;
	}
	
	public static String tabPlmn(String columnName, String PLMN_CODE){
		String val = null;
//		if(!redisCache.hasKey("GSM_INTL_PLMN_CODE")){
//			log.error("reids does not load key[GSM_INTL_PLMN_CODE].");
//			return val;
//		}
		List<Map> list = (List)getCachingRedisMapData("GSM_INTL_PLMN_CODE", PLMN_CODE);
		if(list == null){
			return val;
		}
		if(list.size() > 0){
			val = (String)list.get(0).get(columnName);
		}
		return val;
	}
	
	public static String getUpSubject(String columnName, String ACC_CODE){
		String val = null;
//		if(!redisCache.hasKey("UP_SUBJECT")){
//			log.error("reids does not load key[UP_SUBJECT].");
//			return val;
//		}
		List<Map> list = (List)getCachingRedisMapData("UP_SUBJECT", ACC_CODE);
		if(list == null){
			return val;
		}
		if(list.size() > 0){
			val = (String)list.get(0).get(columnName);
		}
		return val;
	}
	
	
	public static String getOwnSp(String columnName, String SP_CODE){
		String val = null;
//		if(!redisCache.hasKey("OWNPROD_INFO_DTL")){
//			log.error("reids does not load key[OWNPROD_INFO_DTL].");
//			return val;
//		}
		List<Map> list = (List)getCachingRedisMapData("OWNPROD_INFO_DTL", SP_CODE);
		if(list == null){
			return val;
		}
		if(list.size() > 0){
			val = (String)list.get(0).get(columnName);
		}
		return val;
	}
	
	
	public static String getOwnSp(String columnName, String SP_CODE, String timeDay){
		String val = null;
//		if(!redisCache.hasKey("OWNPROD_INFO_DTL")){
//			log.error("reids does not load key[OWNPROD_INFO_DTL].");
//			return val;
//		}
		List list = (List)getCachingRedisMapData("OWNPROD_INFO_DTL", SP_CODE);
		if(list == null){
			return val;
		}
		for(int i=0; i < list.size(); i++){
			Map<String, String> record = (Map)list.get(i);
			if(timeDay.compareTo(record.get("VALID_DATE")) >= 0 && timeDay.compareTo(record.get("EXPIRE_DATE")) < 0){
				val = record.get(columnName);
				break;
			}
		}
		return val;
	}
	
	
	
	public static String getOwnProdInfo(String columnName, String SP_CODE, String OPERATOR_CODE, String timeDay){
		String val = null;
//		if(!redisCache.hasKey("OWNPROD_INFO_DTL_2")){
//			log.error("reids does not load key[OWNPROD_INFO_DTL_2].");
//			return val;
//		}
		List list = (List)getCachingRedisMapData("OWNPROD_INFO_DTL_OPER", SP_CODE + "|" + OPERATOR_CODE);
		if(list == null){
			return val;
		}
		for(int i=0; i < list.size(); i++){
			Map<String, String> record = (Map)list.get(i);
			if(timeDay.compareTo(record.get("VALID_DATE")) >= 0 && timeDay.compareTo(record.get("EXPIRE_DATE")) < 0){
				val = record.get(columnName);
				break;
			}
		}
		return val;
	}
	
	
	public static String getOwnProdInfo(String columnName, String SP_CODE, String OPERATOR_CODE){
		String val = null;
//		if(!redisCache.hasKey("OWNPROD_INFO_DTL")){
//			log.error("reids does not load key[OWNPROD_INFO_DTL].");
//			return val;
//		}
		List<Map> list = (List)getCachingRedisMapData("OWNPROD_INFO_DTL_OPER", SP_CODE + "|" + OPERATOR_CODE);
		//List<Map> list = (List)getCachingRedisMapData("OWNPROD_INFO_DTL", SP_CODE);
		if(list == null || OPERATOR_CODE == null){
			return val;
		}
//		for(int i=0; i < list.size(); i++){
//			Map<String, String> record = (Map)list.get(i);
//			if(OPERATOR_CODE.equals(record.get("OPERATOR_CODE"))){
//				val = record.get(columnName);
//				break;
//			}
//		}
		if(list.size() > 0){
			val = (String)list.get(0).get(columnName);
		}
		return val;
	}
	
	
	
	public static String getAiDailydongman(String columnName, String CONTENT_ID, String timeDay){
		String val = null;
//		if(!redisCache.hasKey("AI_DAILYDONGMAN")){
//			log.error("reids does not load key[AI_DAILYDONGMAN].");
//			return val;
//		}
		List list = (List)getCachingRedisMapData("AI_DAILYDONGMAN", CONTENT_ID);
		if(list == null){
			return val;
		}
		for(int i=0; i < list.size(); i++){
			Map<String, String> record = (Map)list.get(i);
			if(timeDay.compareTo(record.get("VALID_DATE")) >= 0 && timeDay.compareTo(record.get("EXPIRE_DATE")) < 0){
				val = record.get(columnName);
				break;
			}
		}
		return val;
	}
	
	
	
	public static String getAiDailydongman(String columnName, String CONTENT_ID){
		String val = null;
//		if(!redisCache.hasKey("AI_DAILYDONGMAN")){
//			log.error("reids does not load key[AI_DAILYDONGMAN].");
//			return val;
//		}
		List<Map> list = (List)getCachingRedisMapData("AI_DAILYDONGMAN", CONTENT_ID);
		if(list == null){
			return val;
		}
		if(list.size() > 0){
			val = (String)list.get(0).get(columnName);
		}
		return val;
	}
	
	
	
	public static String getProd(String columnName, String EXTEND_ID){
		String val = null;
//		if(!redisCache.hasKey("UP_OFFER_PRODUCT_ITEM")){
//			log.error("reids does not load key[UP_OFFER_PRODUCT_ITEM].");
//			return val;
//		}
		List<Map> list = (List)getCachingRedisMapData("UP_OFFER_PRODUCT_ITEM", EXTEND_ID);
		if(list == null){
			return val;
		}
		if(list.size() > 0){
			val = (String)list.get(0).get(columnName);
		}
		return val;
	}
	
	
	public static String getContent(String columnName, String SP_CODE, String GAME_ID, String timeDay){
		String val = null;
//		if(!redisCache.hasKey("QRY_GAME_CONTENTS")){
//			log.error("reids does not load key[QRY_GAME_CONTENTS].");
//			return val;
//		}
		List list = (List)getCachingRedisMapData("QRY_GAME_CONTENTS", SP_CODE + "|" + GAME_ID);
		if(list == null){
			return val;
		}
		for(int i=0; i < list.size(); i++){
			Map<String, String> record = (Map)list.get(i);
			if(timeDay.compareTo(record.get("VALID_DATE")) >= 0 && timeDay.compareTo(record.get("EXPIRE_DATE")) < 0){
				val = record.get(columnName);
				break;
			}
		}
		return val;
	}
	
	
	public static String getContent(String columnName, String SP_CODE, String GAME_ID){
		String val = null;
//		if(!redisCache.hasKey("QRY_GAME_CONTENTS")){
//			log.error("reids does not load key[QRY_GAME_CONTENTS].");
//			return val;
//		}
		List<Map> list = (List)getCachingRedisMapData("QRY_GAME_CONTENTS", SP_CODE + "|" + GAME_ID);
		if(list == null){
			return val;
		}
		if(list.size() > 0){
			val = (String)list.get(0).get(columnName);
		}
		return val;
	}
	
	
	
	public static String getIsmg(String columnName, String SP_CODE, String OPERATOR_CODE, String timeDay){
		String val = null;
//		if(!redisCache.hasKey("ISMG_RATE")){
//			log.error("reids does not load key[ISMG_RATE].");
//			return val;
//		}
		List list = (List)getCachingRedisMapData("ISMG_RATE", SP_CODE + "|" + OPERATOR_CODE);
		if(list == null){
			return val;
		}
		for(int i=0; i < list.size(); i++){
			Map<String, String> record = (Map)list.get(i);
			if(timeDay.compareTo(record.get("VALID_DATE")) >= 0 && timeDay.compareTo(record.get("EXPIRE_DATE")) < 0){
				val = record.get(columnName);
				break;
			}
		}
		return val;
	}
	
	
	
	public static String getIsmg(String columnName, String SP_CODE, String OPERATOR_CODE){
		String val = null;
//		if(!redisCache.hasKey("ISMG_RATE")){
//			log.error("reids does not load key[ISMG_RATE].");
//			return val;
//		}
		List<Map> list = (List)getCachingRedisMapData("ISMG_RATE", SP_CODE + "|" + OPERATOR_CODE);
		if(list == null){
			return val;
		}
		if(list.size() > 0){
			val = (String)list.get(0).get(columnName);
		}
		return val;
	}
	
	
	
	public static String getSp(String columnName, String SP_CODE,  String timeDay){
		String val = null;
//		if(!redisCache.hasKey("ISMG_SP_INFO_DTL")){
//			log.error("reids does not load key[ISMG_SP_INFO_DTL].");
//			return val;
//		}
		List list = (List)getCachingRedisMapData("ISMG_SP_INFO_DTL", SP_CODE);
		if(list == null){
			return val;
		}

		for(int i=0; i < list.size(); i++){
			Map<String, String> record = (Map)list.get(i);
			if(timeDay.compareTo(record.get("VALID_DATE")) >= 0 && timeDay.compareTo(record.get("EXPIRE_DATE")) < 0){
				val = record.get(columnName);
				break;
			}
		}
		return val;
	}
	
	
	
	
	public static String getSp(String columnName, String SP_CODE){
		String val = null;
		
//		if(!redisCache.hasKey("ISMG_SP_INFO_DTL")){
//			log.error("reids does not load key[ISMG_SP_INFO_DTL].");
//			return val;
//		}
		List<Map> list = (List)getCachingRedisMapData("ISMG_SP_INFO_DTL", SP_CODE);
		
		if(list != null && list.size() > 0){
			val = (String)list.get(0).get(columnName);
		}
		return val;
	}
	
	/*
	 * BILL_ID:用户手机号
	 * OPP_NUMBER:对方手机号
	 * timeDay：话单时间
	 */
	
	public static boolean getShieldInfoDtl(String BILL_ID, String OPP_NUMBER, String timeDay){
		boolean val = false;
//		if(!redisCache.hasKey("SHIELD_INFO_DTL")){
//			log.error("reids does not load key[SHIELD_INFO_DTL].");
//			return val;
//		}
		List list = (List)getCachingRedisMapData("SHIELD_INFO_DTL", BILL_ID);
		if(list == null){			
			return val;
		}
		for(int i=0; i < list.size(); i++){
			Map<String, String> record = (Map)list.get(i);
			if(OPP_NUMBER.equals(record.get("OPP_NUMBER")) && timeDay.compareTo(record.get("VALID_DATE")) > 0 && timeDay.compareTo(record.get("EXPIRE_DATE")) < 0){
				val = true;
				break;
			}
		}
		return val;
	}

	
	//Map<Integer, Map<String, String> --> <201403, <1, rowkey>>
	/**
	 * 从redis中获取分页信息
	 * @param key
	 * @param startIndex
	 * @return
	 */
	public static Map<String, String> getPageStartKey(String key,int startIndex) {
		return (Map<String,String>)((RedisCache)redisCache).getRedisTemplate().opsForHash().get(key,startIndex);
	}
	
	
	public static Map<String, Integer> getCountInfo(String key) {
        return  (Map<String,Integer>)((RedisCache)redisCache).getRedisTemplate().opsForHash().get(key,-9);
	}

	public static List getCountAndRowkeyInfo(String key,int startIndex) {
        Collection<Object> list = new ArrayList<Object>();
        list.add(-9);
        list.add(startIndex);
        return (List)((RedisCache)redisCache).getRedisTemplate().opsForHash().multiGet(key,list);
	}


	/**
	 * 将分页信息放入redis缓存
	 * 数据格式：{rowkeyInfo={
     *                         51={201402=77e1587108017120140201074744, 201403=77e1587108017120140201000000},
     *                         101={201403=77e1587108017120140301074708}
     *                      },
     *           countInfo={201402=81, 201403=81}}
	 * @param key
	 * @param recordInfo
	 * @param counterInfo
	 * @param startIndex
	 * @param offset
	 */
	public static void putPageStartKeyOrCountToCache(String key, Map<String, List<Map<String, String>>> recordInfo,
                               Map<String, Integer> counterInfo ,int startIndex, int offset, String startKey, int timeout) {
		long startTime = System.currentTimeMillis();
		Iterator it = recordInfo.keySet().iterator();
		String month = (String) it.next();
		List<Map<String, String>> currentTableData = recordInfo.get(month);
		boolean empty =  currentTableData.size() == 0 ? true : false;
		int remain = offset - currentTableData.size();
		int index = currentTableData.size()-1;
		
		while(remain > 0  && it.hasNext()) { //当前月不够，继续查下个月
			month = (String) it.next();
			currentTableData = recordInfo.get(month);
			if(currentTableData != null && currentTableData.size() > 0) {
				empty = false;
			}
			int temp = remain - 1;
			remain = remain - currentTableData.size();
			if(remain <= 0) {
				index = temp;
			} else {
                index = currentTableData.size() -1 ;
            }
		}
		if(empty) {
			((RedisCache)redisCache).getRedisTemplate().expire(key, timeout, TimeUnit.SECONDS);
			return;
		}
		log.debug("当前页信息("+ startIndex +")：");
		log.debug("month=" + month + ", index=" + index +", start key=" + currentTableData.get(0).get("ID")  + ", last rowkey=" + currentTableData.get(index).get("ID"));
		log.debug("下一页("+ (startIndex + offset) +")要查询的信息：");
		byte[] lastKey = currentTableData.get(index).get("ID").getBytes();
		lastKey[lastKey.length - 1] ++;
		String nextStartKey = new String(lastKey);
		log.debug("nextStartKey=" + nextStartKey);
		
		
		//分析下页记录需要查询哪些表
		int nextStartIndex = startIndex + offset;
		int nextStopIndex = startIndex + offset + offset;
		int sum = 0;
		String m = null;
		it = counterInfo.keySet().iterator();

		Map<String, String> info = new LinkedHashMap<String, String>();

		while(it.hasNext()) {
			m = (String) it.next();
			sum += counterInfo.get(m);
			if(m.compareTo(month) == 0) {
				info.put(m, nextStartKey);
			} else if(m.compareTo(month) > 0) {
				info.put(m, startKey);
			}
			if(sum >= nextStopIndex) {
				break;
			}
		}
        ((RedisCache)redisCache).putData2Cache(key, nextStartKey, info, timeout);
//        dump(key);

        log.info("next page info: " + info);
		log.info("put pageInfo to redis token: " + (System.currentTimeMillis() - startTime) + "ms");
	}


    public static void dump(String key) {
        Collection c = ((RedisCache)redisCache).getRedisTemplate().opsForHash().keys(key);
        List list = (ArrayList)((RedisCache)redisCache).getRedisTemplate().opsForHash().multiGet(key, c);
        Map map = new LinkedHashMap();
        int i = 0;
        for(Iterator it = c.iterator();it.hasNext();) {
            map.put(it.next(), list.get(i));
            i++;
        }
        log.info("page info: " + map);
    }


    public static void put(String key, Object hashKey, Object value, long timeout) {
        redisCache.putData2Cache(key, hashKey, value, timeout);
    }
}

