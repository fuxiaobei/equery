package com.asiainfo.billing.drquery.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Log4jConfigUtil {
	
	private final static Log log = LogFactory.getLog(Log4jConfigUtil.class);

	public static void configLog4jProperties(String log4jConfigPath, String logFilePath){
		System.setProperty("DRQuery.root", logFilePath);
		log.info("Set web app root system property: 'DRQuery.root' = [" + logFilePath + "]");
		log.info("load log4j.properties from [" + log4jConfigPath + "]");
		
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		Properties props = new Properties();
		
		try {

			InputStream in = classloader.getResourceAsStream(log4jConfigPath);
			if(in == null){
				throw new RuntimeException("log4j.properties[" + log4jConfigPath + "] is not found.");
			}
			props.load(in);
			PropertyConfigurator.configure(props);
		} catch (IOException e) {
			throw new RuntimeException("load log4j.properties ocur exception.", e);
		}
	}
	
	public static void main(String[] args) throws Exception{
		Logger log = MonitorLogger.getMonitorLogger();
		while(true){
			log.info("---2 " + System.currentTimeMillis());
			Thread.currentThread().sleep(2000);
		}
	}
}
