package com.asiainfo.billing.drquery.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Miscellaneous Json utility methods.
 * <p>
 * Mainly for internal use within the osfc; consider <a href=
 * "http://opensourcejavaphp.net/java/json/net/sf/json/util/JSONUtils.java.html"
 * >JSONUtils</a> for a more comprehensive suite of Json utilities.
 * 
 * @author Rex Wong
 */
public final class JsonUtils {
	private static final Log logger = LogFactory.getLog(JsonUtils.class);
	/**
	 * @param strJson
	 * @return
	 */
	public static JSONObject string2JsonObject(String strJson) {
		if(StringUtils.isEmpty(strJson)){
			throw new UtilsNestedException("The input string's value is empty,we cant convert is into JsonObject");
		}
		JSONObject jSONObject = null;
		try{
			jSONObject = JSONObject.fromObject(strJson);
		}
		catch(Exception e){
			throw new UtilsNestedException("convert string[value:"+strJson+"] object to JsonObject failed.",e);
		}
		return jSONObject;
	}

	/**
	 * @param jsonObject
	 * @return
	 */
	public static boolean isJsonArray(Object jsonObject) {
		if (jsonObject instanceof JSONArray) {
			return true;
		}
		if (jsonObject instanceof String) {
			String strJsonObj = (String) jsonObject;
			if (strJsonObj.startsWith("[") && strJsonObj.endsWith("]")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param jsonObject
	 * @return
	 */
	public static boolean isJsonArray(String jsonObject) {
		if (jsonObject.startsWith("[") && jsonObject.endsWith("]")) {
			return true;
		}
		return false;
	}

	public static void buildJsonObj2Object(JSONObject jsonObject, Object target)
			throws Exception {
		@SuppressWarnings("unchecked")
		Map<String, Class<?>> jsonKeymap = JSONUtils.getProperties(jsonObject);
		for (String key : jsonKeymap.keySet()) {
			Object value = jsonObject.get(key);
			Field fieldType = resolveField4Cache(target.getClass(), key);
			if(fieldType==null){
				if (logger.isErrorEnabled()) {
					logger.error("The key[" + key
							+ "] from jsonObject can't resolve Field in target["
							+ target.getClass().getName() + "] Object");
				}
				continue;
			}
			if (isCollectionProperty(fieldType)) {
				Class<?> genericClazz = resolvePropGengericType(fieldType);
				value = resolveCollectionProperty(value, genericClazz);
			}
			else if(value instanceof JSONObject){
				Class<?> fieldTypeClazz = fieldType.getType();
				Object fieldObject = invokeConstructor(fieldTypeClazz);
				buildJsonObj2Object((JSONObject)value,fieldObject);
				value = fieldObject;
			}
			else{
				/** 如果属性值为JSONNull，直接将vlaue赋为null **/
				if(value instanceof net.sf.json.JSONNull){
					value = null;
				}else{
					String targetClazz = fieldType.getType().getName();
					String sourceClazz = value.getClass().getName();
					if(!targetClazz.equals(sourceClazz)){
						value = ConvertUtils.convert(value, Class.forName(targetClazz)) ;
					}
				}
			}
			PropertyUtils.setProperty(target, key, value);
		}
	}

	/**
	 * returning new instance of <code>targetClazz</code>
	 * 
	 * @param targetClazz
	 * @return
	 */
	private static Object invokeConstructor(Class<?> targetClazz) {
		Object target = null;
		try {
			target = ConstructorUtils.invokeConstructor(targetClazz, null);
		} catch (NoSuchMethodException e) {
			new IllegalArgumentException(targetClazz
					+ "'s constructor cannot be found");
		} catch (IllegalAccessException e) {
			new IllegalArgumentException(
					"an error occurs accessing the constructor in "
							+ targetClazz);
		} catch (InvocationTargetException e) {
			new IllegalArgumentException(
					"an error occurs invoking the constructor in "
							+ targetClazz);
		} catch (InstantiationException e) {
			new IllegalArgumentException("an error occurs instantiating the "
					+ targetClazz);
		}
		return target;
	}

	public static Object resolveCollectionProperty(Object jsonvalue,
			Class<?> targetClazz) throws Exception {
		if (jsonvalue == null || jsonvalue.equals("null")) {
			if(logger.isErrorEnabled()){
				logger.error("the jsonvalue param value is null,can't convert this to collection meta data");
			}
			return null;
		}
		Object returnObj = null;
		if (jsonvalue instanceof String) {
			boolean isArray = JsonUtils.isJsonArray((String) jsonvalue);
			if (isArray) {
				JSONArray jsonArray = JSONArray.fromObject(jsonvalue);
				returnObj = buildJsonArray2Collection(jsonArray, targetClazz);
			} else {
				JSONObject jsonObject = JSONObject.fromObject(jsonvalue);
				Object target = invokeConstructor(targetClazz);
				buildJsonObj2Object(jsonObject, target);
				returnObj = target;
			}
		} else if (jsonvalue instanceof JSONArray) {
			returnObj = buildJsonArray2Collection((JSONArray) jsonvalue, targetClazz);
		} else if (jsonvalue instanceof JSONObject) {
			Object target = invokeConstructor(targetClazz);
			buildJsonObj2Object((JSONObject) jsonvalue, target);
			returnObj = target;
		}
		return returnObj;
	}

	private static Map<Class<?>, ClassMeta> cachingClassMeta = new HashMap<Class<?>, ClassMeta>();

	/**
	 * resolve type of field.<br>
	 * we get type of member variable from cache at first.and we will handler
	 * all member variables type into cache when targetCazz not in cache. this
	 * method should put in ClassUtils.
	 * 
	 * @param targetClazz
	 * @param feildName
	 * @return
	 */
	private static Field resolveField4Cache(Class<?> targetClazz, String feildName) {
		ClassMeta meta = cachingClassMeta.get(targetClazz);
		Map<String, Field> fieldTypeMap = null;
		if (meta != null) {
			fieldTypeMap = meta.getFieldTypeMap();
			return fieldTypeMap.get(feildName);
		} else {
			boolean isDebugEnabled = logger.isDebugEnabled();
			if(isDebugEnabled){
				logger.debug("start set feild cache into ["+targetClazz+"]");
			}
			meta = new ClassMeta();
			Class<?> superClass = targetClazz.getSuperclass();
			Field[] fields = null;
			if(superClass!=null){
				fields = superClass.getDeclaredFields();
			}
			fields = ArrayUtils.addAll(fields, targetClazz.getDeclaredFields());
			fieldTypeMap = new HashMap<String, Field>();
			for (Field field : fields) {
				if(isDebugEnabled){
					logger.debug("resolve feild ["+field.getName()+"] into cache of ["+targetClazz.getSimpleName()+"]");
				}
				fieldTypeMap.put(field.getName(), field);
			}
			meta.setFieldTypeMap(fieldTypeMap);
			cachingClassMeta.put(targetClazz, meta);
			if(isDebugEnabled){
				logger.debug("end set feild cache into ["+targetClazz+"]");
			}
		}
		Field fieldType = fieldTypeMap.get(feildName);
		return fieldType;
	}

	/**
	 * whether property type is collection
	 * 
	 * @param field
	 * @return true:type is collection
	 */
	private static boolean isCollectionProperty(Field field) {
		if (field.getGenericType() instanceof ParameterizedType) {
			return true;
		}
		return false;
	}
	/**
	 * gain generic type of collection
	 * 
	 * @param field
	 * @return
	 */
	private static Class<?> resolvePropGengericType(Field field) {
		ParameterizedType type = (ParameterizedType) field.getGenericType();
		Type[] genericTypes = type.getActualTypeArguments();
		Class<?> genericTypeClazz = (Class<?>) genericTypes[0];
		return genericTypeClazz;
	}

	private static class ClassMeta {
		Map<String, Field> fieldTypeMap;

		public Map<String, Field> getFieldTypeMap() {
			return fieldTypeMap;
		}

		public void setFieldTypeMap(Map<String, Field> fieldTypeMap) {
			this.fieldTypeMap = fieldTypeMap;
		}

	}

	/**
	 * @param jsonArray
	 * @param target
	 * @return
	 * @throws Exception
	 */
	public static Collection<Object> buildJsonArray2Collection(
			JSONArray jsonArray, Class<?> targetClazz) throws Exception {
		Collection<Object> collection = new ArrayList<Object>();
		for (int i = 0; i < jsonArray.size(); i++) {
			Object target = invokeConstructor(targetClazz);
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			buildJsonObj2Object(jsonObject, target);
			collection.add(target);
		}

		return collection;
	}

	/**
	 * Creates a Map with all the properties of the JSONObject.
	 */
	public static Map getProperties(JSONObject jsonObject) {
		return JSONUtils.getProperties(jsonObject);
	}

     public static Map parserToMap(String s){
		Map map=new HashMap();
		JSONObject json=JSONObject.fromObject(s);
		Iterator keys=json.keys();
		while(keys.hasNext()){
			String key=(String) keys.next();
			String value=json.get(key).toString();
			if(value.startsWith("{")&&value.endsWith("}")){
				map.put(key, parserToMap(value));
			}else{
				map.put(key, value);
			}
		}
		return map;
	}


    public static void main(String[] args) throws Exception{
//                String jsonStr = "{\n" +
//                "    \"qryId\": \"1111111122\",\n" +
//                "    \"phoneNo\": \"18601134210\",\n" +
//                "    \"startTime\": \"20131011200000\",\n" +
//                "    \"endTime\": \"20131011225959\",\n" +
//                "    \"groupColumnCode\": \"appName\",\n" +
//                "    \"topNum\": \"20\",\n" +
//                "    \"opId\": \"11111\",\n" +
//                "    \"opName\": \"张三\",\n" +
//                "    \"srcSystemCode\": \"CMOD\"\n" +
//                "}";
//         Map<String,String>  map = parserToMap(jsonStr);
//         String startTime = map.get("startTime");
//         DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
//         System.out.println(df.parse(startTime));

        JSONObject joo=new JSONObject();
        JSONObject jo=new JSONObject();
		jo.put("qryId","1111111122");
		jo.put("phoneNo","15872432226");
		jo.put("startTime","20131030000000");
		jo.put("endTime","20131030235900");
		jo.put("groupColumnCode","appId");
		jo.put("topNum","20");
		jo.put("opId","11111");
		jo.put("opName","张三");
		jo.put("srcSystemCode","CMOD");
		joo.put("qryCond", jo);
        System.out.println("qryCond = " + joo.toString());

        String jsonStr = joo.toString();
        Map<String ,Object> jsonArgs = JsonUtils.parserToMap(jsonStr);
        Object obj = jsonArgs.get("qryCond");
        Map<String,String> jsonArgsStr = new HashMap<String,String>();
        if(obj instanceof Map){
            jsonArgsStr = (Map)obj;
        }
        String startTime =  jsonArgsStr.get("startTime");
        System.out.println("startTime = " + startTime);
    }
}
