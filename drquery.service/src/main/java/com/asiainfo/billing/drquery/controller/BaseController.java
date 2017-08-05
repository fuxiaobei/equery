package com.asiainfo.billing.drquery.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.asiainfo.billing.drquery.utils.MonitorLogger;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.asiainfo.billing.drquery.utils.JsonUtils;

public class BaseController extends MultiActionController {
	
	private static Log log = LogFactory.getLog(BaseController.class);
	
	protected <T> T getParameterMapFromBody(HttpServletRequest request,T paramObj) {
		BufferedReader in = null;
		String encoding=request.getCharacterEncoding();
		if(encoding==null||encoding.equals(""))encoding="UTF-8";
		try {
			in = new BufferedReader(new InputStreamReader(request.getInputStream()));
			String jsonParam="";
			String line;
			while ((line = in.readLine()) != null) {
				jsonParam +=line;
			}
			MonitorLogger.debug(log, "request param: " + jsonParam);
			JSONObject jSONObject = JsonUtils.string2JsonObject(jsonParam);
			JsonUtils.buildJsonObj2Object(jSONObject, paramObj);
			
		} 
		catch (Exception e) {
			logger.error("read parameter from body failed", e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					logger.error("close stream failed", e);
				}
			}
		}
		return paramObj;
	}
	@SuppressWarnings("unchecked")
	protected Map<String,String> getHeaders(HttpServletRequest req){
		Enumeration<String> enumeration = req.getHeaderNames();
		Map<String,String> headerMap = new HashMap<String,String>();
		while(enumeration.hasMoreElements()){
			String key = enumeration.nextElement();
			String headervalue = req.getHeader(key);
			headerMap.put(key, headervalue);
		}
		return headerMap;
	}
}
