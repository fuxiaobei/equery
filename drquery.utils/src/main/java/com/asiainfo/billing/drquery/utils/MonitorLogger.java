package com.asiainfo.billing.drquery.utils;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public class MonitorLogger {

	private static String drquery_log_level = PropertiesUtil.getProperty("drquery.service/runtime.properties","DRQUERY_LOG_LEVEL");
	private static String dataPattern = PropertiesUtil.getProperty("drquery.service/runtime.properties","monitorLog.datePattern");
	private static String env_log_level = System.getenv(drquery_log_level);
	private static Log log = LogFactory.getLog(MonitorLogger.class);
	private static Logger monitorLogger;
	
	/**
	 * 根据操作系统环境输出debug日志
	 * @param log
	 * @param obj
	 */
	public static void debug(Log log, Object obj){
		if("debug".equals(env_log_level)){
			log.info("[debug] ----------> " + obj);
		}
	}
	
	public static boolean isDebug(){
		return "debug".equals(env_log_level);
	}
	
	private static Logger createMonitorLogger(){
        Logger logger = Logger.getLogger("DrqueryMonitor");
        logger.removeAllAppenders();
        //设置Logger级别。
        logger.setLevel(Level.INFO);
        //设置是否继承Logger。
        //默认为true
        logger.setAdditivity(true);
        // 生成新的Appender
        DailyRollingFileAppender appender = new DailyRollingFileAppender();
        PatternLayout layout = new PatternLayout();
        //log的输出形式
        //String conversionPattern = "[%d] %p %t %c - %m%n";
        String conversionPattern = "%m%n";
        layout.setConversionPattern(conversionPattern);
        appender.setLayout(layout);       
        if(dataPattern == null || "".equals(dataPattern))
        	dataPattern="yyyyMMdd";
        appender.setDatePattern("'.'"+dataPattern);

        //日志文件路径
        String qryLogPath = System.getProperty("drquery.monitorLog.path");
        if(StringUtils.isEmpty(qryLogPath)){
            qryLogPath = System.getProperty("webAppRootPath") + File.separator + "logs";
        	log.warn("system property: 'drquery.monitorLog.path' is not set in jvm arguments, use default path: " + qryLogPath);
        }
        String fileName = "drquery_monitor.log";
        appender.setFile(qryLogPath + File.separator + 
        		(System.getProperty("drquery.serverName") == null ? "" : System.getProperty("drquery.serverName")) +File.separator +fileName);
        //log的文字码
        appender.setEncoding("UTF-8");
        //true:在已存在log文件后面追加 false:新log覆盖以前的log
        appender.setAppend(true);
        //适用当前配置
        appender.activateOptions();
        //将新的Appender加到Logger中
        logger.addAppender(appender);
        return logger;
	}
	
	public static Logger getMonitorLogger() {
		if(monitorLogger == null){
			monitorLogger = createMonitorLogger();
		}
		return monitorLogger;
    }


	public static void main(String[] args) throws Exception{
		System.setProperty("drquery.monitorLog.path", "d:/logs/");
		Logger log = getMonitorLogger();
		while(true){
			log.info("--- " + System.currentTimeMillis());
			Thread.currentThread().sleep(2000);
		}
	}
}
