package com.asiainfo.billing.drquery.exception;

public class BusinessException extends BaseException{

	public BusinessException(String code){
		super(code);
	}
	
	public BusinessException(String code, Object[] values){
		super(code, values);
	}
}
