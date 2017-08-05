package com.asiainfo.billing.drquery.process.compile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.billing.drquery.process.compile.model.BeanConfig;

public class BeanHolder {
	
	private final static Log log = LogFactory.getLog(BeanHolder.class);

	private static List<BeanConfig> beanConfigList = new ArrayList<BeanConfig>();
	private static Map<String, Class> beanClasses = new HashMap<String, Class>();
	private static Map<String, Object> beanInstances = new HashMap<String, Object>();
	
	public static void add(BeanConfig bean){
		beanConfigList.add(bean);
	}

	public static List<BeanConfig> getBeanConfigList() {
		return beanConfigList;
	}

	public static void setBeanConfigList(List<BeanConfig> beanConfigList) {
		BeanHolder.beanConfigList = beanConfigList;
	}
	
	@SuppressWarnings("rawtypes")
	public static void putClass(String beanId, Class clz){
		beanClasses.put(beanId, clz);
		instanceClass(beanId, clz);
	}
	
	@SuppressWarnings("rawtypes")
	public static void instanceClass(String beanId, Class clz){
		try{
			Object instance = clz.newInstance();
			beanInstances.put(beanId, instance);
			log.info("Instance class[" + clz + "] success! ");
		}catch(Exception e){
			throw new RuntimeException("Instance class[" + clz + "] failture! ", e);
		}
	}
	
	public static Object getInstance(String beanId){
		return beanInstances.get(beanId);
	}
}
