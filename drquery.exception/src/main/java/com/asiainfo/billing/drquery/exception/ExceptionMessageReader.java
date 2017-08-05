package com.asiainfo.billing.drquery.exception;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

@SuppressWarnings("unchecked")
public class ExceptionMessageReader{
	
	private String globalLogLevel = null;
	private boolean globalLogLevelOverride;
	private boolean globalPrintStackTrace;
	
	private Map<String, ExceptionMessage> exceptionMessage = new HashMap<String, ExceptionMessage>();
	
	private String[] levels = new String[]{"debug", "info", "warn", "error", "fatal", "no"};
	
	private SAXReader reader = new SAXReader();
	
	
	public void readConfig(){
		/**Exception Config Path**/
		String path = "classpath:drquery.exception/exceptionMessages.xml";
		
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();  
	    try {
			Resource[] resources= resolver.getResources(path);
			
			for(Resource resource : resources){
				InputStream inputStream = resource.getInputStream();
				Document document = reader.read(inputStream);
				Element root = document.getRootElement();
				
				Element globalLogLevelEl =  root.element("globalLogLevel");
				if(globalLogLevelEl != null){
					globalLogLevel = globalLogLevelEl.getText();
					validateLogLevel(globalLogLevel, "<globalLogLevel>");
				}
				
				Element globalLogLevelOverrideEl =  root.element("globalLogLevelOverride");
				if(globalLogLevelOverrideEl != null){
					String _globalLogLevelOverride = globalLogLevelOverrideEl.getText();
					if(!"true".equals(_globalLogLevelOverride) && !"false".equals(_globalLogLevelOverride)){
						throw new DrqueryRuntimeException("<globalLogLevelOverride> should be true or false for exceptionMessage.xml");
					}
					globalLogLevelOverride = Boolean.valueOf(_globalLogLevelOverride);
				}
				
				Element globalPrintStackTraceEl =  root.element("globalPrintStackTrace");
				if(globalLogLevelOverrideEl != null){
					String _globalPrintStackTrace = globalPrintStackTraceEl.getText();
					if(!"true".equals(_globalPrintStackTrace) && !"false".equals(_globalPrintStackTrace)){
						throw new DrqueryRuntimeException("<globalPrintStackTrace> should be true or false for exceptionMessage.xml");
					}
					globalPrintStackTrace = Boolean.valueOf(_globalPrintStackTrace);
				}
				
				List<Element> exceptions = root.elements("exception");
				for(Element ex : exceptions){
					ExceptionMessage exMessage = new ExceptionMessage();
					
					Element codeEl = ex.element("code");
					String code = null;
					if(codeEl != null){
						code = codeEl.getText();
					}
					if(StringUtils.isEmpty(code)){
						throw new DrqueryRuntimeException("<Code> is required for exceptionMessage.xml");
					}
					Element messageEl = ex.element("message");
					String message = null;
					if(messageEl != null){
						message = messageEl.getText();
					}
					if(StringUtils.isEmpty(message)){
						throw new DrqueryRuntimeException("<Message> is required for exceptionMessage.xml");
					}
					
					String logLevel = null;
					Element logLevelEl = ex.element("logLevel");
					if(logLevelEl != null){
						logLevel = logLevelEl.getText();
						validateLogLevel(logLevel, "<logLevel>");
					}
					
					if(logLevel != null){
						if(globalLogLevel != null && globalLogLevelOverride){
							logLevel = globalLogLevel;
						}
					}else if(globalLogLevel != null){
						logLevel = globalLogLevel;
					}else{
						logLevel = "info";
					}
					
					Element descEl = ex.element("desc");
					if(descEl != null){
						exMessage.setDesc(descEl.getText());
					}
					
					exMessage.setCode(code);
					exMessage.setMessage(message);
					exMessage.setLogLevel(logLevel);
					if(exceptionMessage.containsKey(code)){
						throw new DrqueryRuntimeException("code=" + code + " is duplicate in " + path);
					}
					exceptionMessage.put(code, exMessage);
				}
			}
		} catch (Exception e) {
			throw new DrqueryRuntimeException("init " + path + " exception.", e);
		} 
	}
	
	public void validateLogLevel(String level, String attributeName){
		boolean flag = false;
		for(String l : levels){
			if(l.equals(level)){
				flag = true;
				break;
			}
		}
		if(!flag){
			throw new DrqueryRuntimeException(attributeName + " should be one of [debug, info, warn, error, fatal, no] for exceptionMessage.xml, but current value is [" + level + "].");
		}
	}
	

	public String getGlobalLogLevel() {
		return globalLogLevel;
	}

	public boolean isGlobalLogLevelOverride() {
		return globalLogLevelOverride;
	}

	public boolean isGlobalPrintStackTrace() {
		return globalPrintStackTrace;
	}

	public Map<String, ExceptionMessage> getExceptionMessage() {
		return exceptionMessage;
	}

}

