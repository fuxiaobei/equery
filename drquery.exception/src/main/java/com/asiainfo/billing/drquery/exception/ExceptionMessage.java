package com.asiainfo.billing.drquery.exception;

import java.text.MessageFormat;
import java.util.MissingResourceException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ExceptionMessage {
	
	private final static Log log = LogFactory.getLog(ExceptionMessage.class);

	private String code;
	private String message;
	private String logLevel;
	private String desc;
	
	private Object[] values;
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		try {
		    if (values != null)
		    	message = MessageFormat.format(message, values);
		}
		catch (MissingResourceException missingresourceexception) {
		   log.error("MessageFormat format message: [" + message +"] occur error. " + missingresourceexception.getMessage(), missingresourceexception);
		}
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getLogLevel() {
		return logLevel;
	}
	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}
	
	public Object[] getValues() {
		return values;
	}
	public void setValues(Object[] values) {
		this.values = values;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
	
	
}
