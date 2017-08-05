package com.asiainfo.billing.drquery.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class Log4jConfigListener implements ServletContextListener {
	
	public static final String CONFIG_LOCATION_PARAM = "log4jConfigLocation";
	
	/**
	 * Web app root key parameter at the servlet context level
	 * (i.e. a context-param in <code>web.xml</code>): "webAppRootKey".
	 */
	public static final String WEB_APP_ROOT_KEY_PARAM = "webAppRootKey";

	/** Default web app root key: "webapp.root" */
	public static final String DEFAULT_WEB_APP_ROOT_KEY = "webapp.root";
	
	public static final String WEB_APP_SERVER_NAME_KEY_PARAM = "webAppServerNameKey";
	
	public static final String DEFAULT_WEB_APP_SERVER_NAME_KEY = "webapp.serverName";
	
	private final static Log log = LogFactory.getLog(Log4jConfigListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		LogManager.shutdown();
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		String location = event.getServletContext().getInitParameter(CONFIG_LOCATION_PARAM);
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		Properties props = new Properties();
		try {
			setWebAppRootSystemProperty(event.getServletContext());
			InputStream in = classloader.getResourceAsStream(location);
			if(in == null){
				throw new RuntimeException("log4j.properties[" + location + "] is not found.");
			}
			props.load(in);
			PropertyConfigurator.configure(props);
			log.info("load log4j.properties success.");
		} catch (IOException e) {
			throw new RuntimeException("load log4j.properties ocur exception.", e);
		}
	}
	
	
	public static void setWebAppRootSystemProperty(ServletContext servletContext) throws IllegalStateException {
		//System.setProperty("DRQuery.rootPath", "d:/");
		//System.setProperty("serverName", "127.0.0.1_server1");
		Assert.notNull(servletContext, "ServletContext must not be null");
		String param = servletContext.getInitParameter(WEB_APP_ROOT_KEY_PARAM);
		String key = (param != null ? param : DEFAULT_WEB_APP_ROOT_KEY);
		String rootPath = System.getProperty(key);
		if(rootPath == null || rootPath.equals("")){
			rootPath = servletContext.getRealPath("/");
			if (rootPath == null) {
				throw new IllegalStateException(
				    "Cannot set web app rootPath system property when WAR file is not expanded");
			}
		}
		
		String oldValue = System.getProperty(key);
		if (oldValue != null && !StringUtils.pathEquals(oldValue, rootPath)) {
			throw new IllegalStateException(
			    "Web app rootPath system property already set to different value: '" +
			    key + "' = [" + oldValue + "] instead of [" + rootPath + "] - " +
			    "Choose unique values for the 'webAppRootKey' context-param in your web.xml files!");
		}
		
		String serverNameParam = servletContext.getInitParameter(WEB_APP_SERVER_NAME_KEY_PARAM);
		String serverNameKey = (serverNameParam != null ? serverNameParam : DEFAULT_WEB_APP_SERVER_NAME_KEY);
		String serverNameVal = System.getProperty(serverNameKey);;
		if(oldValue == null || oldValue.equals("")){
			if(serverNameVal == null)
				serverNameVal = "";
		}else{
			
			if(serverNameVal == null || serverNameVal.equals("")){
				SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss.SSS");
				serverNameVal = "server_" + format.format(new Date()) + "_" + (int)(Math.random() * 100000);
				servletContext.log("Warning!!! System property: '" + serverNameKey + "' is not set, use generated property: '" + serverNameKey + "' = [" + serverNameVal + "]");
			}
		}
		
		System.setProperty(key, rootPath);
		System.setProperty(serverNameKey, serverNameVal);
		System.setProperty("webAppRootPath", rootPath);
		servletContext.log("Set web app rootPath system property: '" + key + "' = [" + rootPath + "]");
		servletContext.log("Set web app rootPath system property: '" + serverNameKey + "' = [" + serverNameVal + "]");
	}
}
