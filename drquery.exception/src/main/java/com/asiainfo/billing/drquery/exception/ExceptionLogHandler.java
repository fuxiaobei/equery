package com.asiainfo.billing.drquery.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ExceptionLogHandler implements IExceptionHandler{

	private final static Log log = LogFactory.getLog(ExceptionLogHandler.class);
	
	
	@Override
	public ExceptionMessage handlerException(BaseException e) {
		String code = e.getCode();
		ExceptionContext context = ExceptionContext.getInstance();
		ExceptionMessage exceptionMessage = context.getExceptionMessage(code, e.getValues());
		
		if(exceptionMessage == null){
			exceptionMessage = context.getExceptionMessage(ExceptionCode.ERROR_CODE_1000);
			log.error(e + ": Exception <code> is invalidate, please check the exception <code>[" + code + "]");
		}
		
		String logLevel = exceptionMessage.getLogLevel();
		if(!LOG_NO.equals(logLevel)){
			if(LOG_INFO.equals(logLevel)){
				log.info(exceptionMessage.getMessage(), e);
			}else if(LOG_DEBUG.equals(logLevel)){
				log.debug(exceptionMessage.getMessage(), e);
			}else if(LOG_ERROR.equals(logLevel)){
				log.error(exceptionMessage.getMessage(), e);
			}else if(LOG_WARN.equals(logLevel)){
				log.warn(exceptionMessage.getMessage(), e);
			}else if(LOG_FATAL.equals(logLevel)){
				log.fatal(exceptionMessage.getMessage(), e);
			}
		}
		
		return exceptionMessage;
	}

	
}
