package com.asiainfo.billing.drquery.web.config;

import com.asiainfo.aiox.common.util.UtilProperties;

public class Config {
	private static String rest=null;
	private static String resourceName= "runtime.properties";
	private static String restKey="drquery.rest";
	
	public static String getRest(){
		if(rest==null){
			rest= UtilProperties.getProperty(resourceName, restKey);
		}
		return rest;
	}
        
        public static String getProperty(String key){
        
            return com.asiainfo.aiox.common.util.UtilProperties.getProperty(resourceName, key);
        }
       

}
