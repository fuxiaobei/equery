package com.asiainfo.billing.drquery.exception;

import java.util.HashMap;
import java.util.Map;

public class ExceptionContext {

	private String globalLogLevel;
	
	private boolean globalLogLevelOverride;
	
	private boolean globalPrintStackTrace;
	
	private Map<String, ExceptionMessage> exceptionMessageMap = new HashMap<String, ExceptionMessage>();
	
	private static ExceptionContext context = null;
	
	
	private ExceptionContext(){
	}
	
	public ExceptionMessage getExceptionMessage(String code){
		return exceptionMessageMap.get(code);
	}
	
	public ExceptionMessage getExceptionMessage(String code, Object values[]){
		ExceptionMessage exceptionMessage = exceptionMessageMap.get(code);
		if(exceptionMessage != null){
			exceptionMessage.setValues(values);
		}
		return exceptionMessage;
	}
	
	public static ExceptionContext getInstance(){
		if(context == null){
			context = new ExceptionContext();
			init();
		}
		return context;
	}
	
	public static void init(){
		if(context == null)
			context = new ExceptionContext();
		
		ExceptionMessageReader reader = new ExceptionMessageReader();
		reader.readConfig();
		
		context.exceptionMessageMap = reader.getExceptionMessage();
		context.globalLogLevel = reader.getGlobalLogLevel();
		context.globalLogLevelOverride = reader.isGlobalLogLevelOverride();
		context.globalPrintStackTrace = reader.isGlobalPrintStackTrace();
	}
	
	
	public static void main(String[] args){
		init();
	}
	

	public String getGlobalLogLevel() {
		return globalLogLevel;
	}

	public void setGlobalLogLevel(String globalLogLevel) {
		this.globalLogLevel = globalLogLevel;
	}

	public boolean isGlobalLogLevelOverride() {
		return globalLogLevelOverride;
	}

	public void setGlobalLogLevelOverride(boolean globalLogLevelOverride) {
		this.globalLogLevelOverride = globalLogLevelOverride;
	}

	public boolean isGlobalPrintStackTrace() {
		return globalPrintStackTrace;
	}

	public void setGlobalPrintStackTrace(boolean globalPrintStackTrace) {
		this.globalPrintStackTrace = globalPrintStackTrace;
	}
	
	
}
