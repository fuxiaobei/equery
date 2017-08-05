package com.asiainfo.billing.drquery.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;

public class ExceptionConfigInit implements InitializingBean{

	private final static Log log = LogFactory.getLog(ExceptionConfigInit.class);
	
	@Override
	public void afterPropertiesSet() throws Exception {
		log.info("begin to init exception config.");
		
		ExceptionContext.init();
		
		log.info("end to init exception config.");
	}

}
