package com.asiainfo.billing.drquery.client.config;

import com.asiainfo.billing.drquery.utils.PropertiesUtil;
import java.util.HashMap;
import java.util.Map;


public class Config {
	private static String rest=null;
	private static String resourceName= "drquery.service/runtime.properties";
	private static String restKey="drquery.rest";
        private static String busiTypeKey="drquery.busiType";
	private static Map<String,String> pathMap = new HashMap<String,String>();
        private static Map<Integer,String> busiTypeMap;
	private Config(){}
	public static String getRest(){
		if(rest==null){
			rest= PropertiesUtil.getProperty(resourceName, restKey);
		}
		return rest;
	}
	public static String getPath(String pathKeys){
		String path = pathMap.get(pathKeys);
		if(path==null||path.length()==0){
			path=PropertiesUtil.getProperty(resourceName, pathKeys);
			pathMap.put(pathKeys, path);
		}
		return path;
	}
        
        public static String getBusiType(int busiType){
            if(busiTypeMap==null){
                busiTypeMap=new HashMap<Integer, String>();
                String busiTypeLine=PropertiesUtil.getProperty(resourceName, busiTypeKey);
                String[] busiTypes=busiTypeLine.split(":");
                int size=busiTypes.length;
                for(int i=0;i<size;i++){
                    String[] type=busiTypes[i].split("\\^");
                    busiTypeMap.put(Integer.valueOf(type[0]), type[1]);
                }
            }
            return busiTypeMap.get(busiType);
        }
}
