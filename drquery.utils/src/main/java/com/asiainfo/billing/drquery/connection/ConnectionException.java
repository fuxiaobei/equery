package com.asiainfo.billing.drquery.connection;

import com.asiainfo.billing.drquery.core.DRQueryNestedCheckedException;

public class ConnectionException extends DRQueryNestedCheckedException {
	
	private static final long serialVersionUID = -2946266495682282677L;
	
    public ConnectionException(String msg, Throwable cause) {
		super(msg, cause);
	}
    public String getMessage() {
		String message = super.getMessage();
		Throwable cause = getCause();
		if(cause!=null){
			message = message + cause.getMessage();
		}
		return message;
	}
}
