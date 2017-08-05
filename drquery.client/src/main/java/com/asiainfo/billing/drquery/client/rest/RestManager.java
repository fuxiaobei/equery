package com.asiainfo.billing.drquery.client.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONObject;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.*;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.billing.drquery.client.config.Config;

public class RestManager 
{
	private static int _timeout=7200000;
	private static final String CONTENT_CHARSET = "UTF-8";
	private static Log log = LogFactory.getLog(RestManager.class);

	public static String get(String uri) throws IOException{
		String data = "";

		HttpClient httpClient= new HttpClient();
		httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, CONTENT_CHARSET); 
		httpClient.setConnectionTimeout(_timeout);
		httpClient.setTimeout(_timeout);
		
		GetMethod getMethod = null;
		try{
                        log.debug("send data to http : ["+uri+"]");
			getMethod= new GetMethod();
			getMethod.setURI(new URI(uri,false));
			getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
			getMethod.setRequestHeader("Connection", "close"); 
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				  log.error("Method failed: " + getMethod.getStatusLine());
				  return data;
			}
			
			byte[] dataResponseBody = getMethod.getResponseBody(); 
			data= new String(dataResponseBody,"utf-8");
                        log.debug("recv date from http : ["+data+"]");
		}catch(HttpException e){
			log.error("Please check your provided http address!");
		}catch(IOException e){
			log.error(e.getMessage());
		}catch(Exception e){
			log.error(e.getMessage());
		}finally{
			if(getMethod!=null) getMethod.releaseConnection();
		}
		
		return data;
	}
        
        public static String post(String uri, Map paramMap) throws IOException{
		String data= "";
		HttpClient httpClient= new HttpClient();
		httpClient.setConnectionTimeout(_timeout);
		httpClient.setTimeout(_timeout);
		
		PostMethod method = null;
		try{
			method= new PostMethod();
			method.setURI(new URI(uri, false));
			//param
			if(paramMap!=null){
				java.util.Iterator keys= paramMap.keySet().iterator();
				while(keys.hasNext()){
					String key= (String)keys.next();
					method.setParameter(key, (String)paramMap.get(key));
				}
			}
			int statusCode = httpClient.executeMethod(method);
			if (statusCode != HttpStatus.SC_OK) {
				  log.error("Method failed: " + method.getStatusLine());
			}
			data= new String(method.getResponseBody(),"utf-8");
                        log.debug("recv date from http : ["+data+"]");
		}catch(HttpException e){
			log.error("Please check your provided http address!");
		}catch(IOException e){
			log.error(e.getMessage());
		}catch(Exception e){
			log.error(e.getMessage());
		}finally{
			if(method!=null) method.releaseConnection();
		}
		return data;
	}
	
	public static String postWithJson(Map<String,String> headMap, JSONObject paramMap) throws IOException{
		String data= "";
		HttpClient httpClient= new HttpClient();
		httpClient.setConnectionTimeout(_timeout);
		httpClient.setTimeout(_timeout);
		PostMethod method = null;
		try{
			method = new PostMethod(Config.getRest());
			for(Entry<String,String> entry:headMap.entrySet()){
				method.addRequestHeader(entry.getKey(),entry.getValue());
			}
			RequestEntity entity = new org.apache.commons.httpclient.methods.StringRequestEntity(paramMap.toString(), "text", "UTF-8");
            method.setRequestEntity(entity);
			int statusCode = httpClient.executeMethod(method);
			if (statusCode != HttpStatus.SC_OK) {
				  log.error("Method failed: " + method.getStatusLine());
				  throw new IOException("Method failed: " + method.getStatusLine());
			}
			data= new String(method.getResponseBody(),"utf-8");
                        log.debug("recv date from http : ["+data+"]");
		}catch(HttpException e){
			log.error("Please check your provided http address!");
		}catch(IOException e){
			log.error(e.getMessage());
		}catch(Exception e){
			log.error(e.getMessage());
		}finally{
			if(method!=null) method.releaseConnection();
		}
		return data;
	}
	
	public static String put(String uri, Map params) throws IOException{
		String data= "";
		HttpClient httpClient= new HttpClient();
		
		PutMethod method = null;
		try{
			method= new PutMethod();
			method.setURI(new URI(uri, false));
			//param
			int statusCode = httpClient.executeMethod(method);
			if (statusCode != HttpStatus.SC_OK) {
				  log.error("Method failed: " + method.getStatusLine());
			}
			data= new String(method.getResponseBody(),"utf-8");
		}catch(HttpException e){
			log.error("Please check your provided http address!");
		}catch(IOException e){
			log.error(e.getMessage());
		}catch(Exception e){
			log.error(e.getMessage());
		}finally{
			if(method!=null) method.releaseConnection();
		}
		return data;
	}
	
	public static String delete(String uri) throws IOException{
		String data= "";
		HttpClient httpClient= new HttpClient();
		httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, CONTENT_CHARSET); 
		httpClient.setConnectionTimeout(_timeout);
		httpClient.setTimeout(_timeout);
		
		DeleteMethod method = null;
		try{
			method= new DeleteMethod();
			method.setURI(new URI(uri,false));
			method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
			int statusCode = httpClient.executeMethod(method);
			if (statusCode != HttpStatus.SC_OK) {
				  log.error("Method failed: " + method.getStatusLine());
			}
			data= new String(method.getResponseBody(),"utf-8");
		}catch(HttpException e){
			log.error("Please check your provided http address!");
		}catch(IOException e){
			log.error(e.getMessage());
		}catch(Exception e){
			log.error(e.getMessage());
		}finally{
			if(method!=null) method.releaseConnection();
		}
		return data;
	}

	public static String httpRest(String uri, String method) throws IOException
	{
		return httpRest(uri, null, method);
	}
	
	public static String httpRest(String uri, Map paramMap, String method) throws IOException
	{
		String threadName = Thread.currentThread().getName();
		log.debug("<"+threadName+">"+method+": "+uri);
		
		String result= null;
		if(method.toUpperCase().equals("PUT"))
			result = put(uri, paramMap);
		if(method.toUpperCase().equals("GET"))
			result = get(uri);
		if(method.toUpperCase().equals("POST"))
			result = post(uri, paramMap);
		if(method.toUpperCase().equals("DELETE"))
			result = delete(uri);
		if(result==null)result = "";
		
		return result;
	}
	
	private static String doRest(String uri, String method) throws IOException
	{
		StringBuffer data= new StringBuffer();
		
		if(uri==null || "".equals(uri))
			return "";
		
		URL url = new URL(uri);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setDoOutput(true);
		
		conn.setRequestMethod(method);
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));   
		String input= null;
		while((input = reader.readLine()) != null) {
			data.append(input);
	    }
		conn.disconnect();
		return data.toString();
	} 
}
