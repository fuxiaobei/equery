package com.asiainfo.billing.drquery.exception;

public class DrqueryRuntimeException extends BaseRuntimeException{

	public DrqueryRuntimeException(){
		super();
	}
	
	public DrqueryRuntimeException(String message){
		super(message);
	}
	
	public DrqueryRuntimeException(Throwable cause){
		super(cause);
	}
	
	public DrqueryRuntimeException(String message, Throwable cause){
		super(message, cause);
	}
}
