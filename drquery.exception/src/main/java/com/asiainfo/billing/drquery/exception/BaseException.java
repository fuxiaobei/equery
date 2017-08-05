package com.asiainfo.billing.drquery.exception;

public class BaseException extends Exception{

	
	public BaseException(String code){
		super();
		this.code = code;
	}
	
	public BaseException(String code, Object[] values){
		super();
		this.code = code;
		this.values = values;
	}
	
	
	private String code;
	private Object[] values;
	
	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Object[] getValues() {
		return values;
	}

	public void setValues(Object[] values) {
		this.values = values;
	}
	
	
}
