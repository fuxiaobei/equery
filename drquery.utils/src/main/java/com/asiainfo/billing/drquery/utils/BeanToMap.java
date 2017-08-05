package com.asiainfo.billing.drquery.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class BeanToMap {

    private final static Log log = LogFactory.getLog(BeanToMap.class);

	public static Map<String, Object> copyProperties(Object obj) {
		Map<String,Object> retMap = new HashMap<String, Object>();
        Method[] mergeMethods = null;
        Field[] mergeFields = null;
        Class clazz = obj.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        Field[] fieldArr = clazz.getFields();
        if (clazz.getGenericSuperclass() != null) {
            Class superClass = clazz.getSuperclass();// 父类
            Method[] superMethods = superClass.getDeclaredMethods();
            mergeMethods = new Method[methods.length+superMethods.length];
            System.arraycopy(methods,0,mergeMethods,0,methods.length);
            System.arraycopy(superMethods,0,mergeMethods,methods.length,superMethods.length);
            Field[] superFields = superClass.getDeclaredFields();//父类变量
            mergeFields = new Field[fieldArr.length+superFields.length];
            System.arraycopy(fieldArr,0,mergeFields,0,fieldArr.length);
            System.arraycopy(superFields,0,mergeFields,fieldArr.length,superFields.length);
        }
        if(mergeMethods == null) return null;
        if(mergeFields == null) return null;
        for(Field field:mergeFields){
            String fieldName = field.getName();
            String firstChar = new String(""+fieldName.charAt(0)).toUpperCase();
            StringBuffer buf = new StringBuffer(fieldName).replace(0,1,firstChar);
            String getMethodName = "get"+buf.toString();
            for(Method method : mergeMethods){
                if(method.getName().equals(getMethodName)){
                    Object ret = null;
                    try {
                        ret = method.invoke(obj,null);
                    } catch (Exception e) {
                       log.info("invoke method  error : " + e.getMessage());
                       return null;
                    }
                    retMap.put(fieldName,ret);
                }
            }
        }
		return retMap;
	}
}
