package com.asiainfo.billing.drquery.infogather;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author Rex Wong
 *
 * @version
 */
public class InfoCollector {
	private static final Log log = LogFactory.getLog(InfoCollector.class);
	
	public static void collectior(Throwable e,String errorInfo) {
		String errorMsg=SysExceptionUtils.buildMessage(errorInfo, e);
		Throwable ex = e.getCause();
		while(true){
			if(ex==null){
				break;
			}
			errorMsg+=SysExceptionUtils.buildMessage(ex.getMessage(), ex);
			ex = ex.getCause();
		}
		log.error(errorMsg, e);
	}
}
