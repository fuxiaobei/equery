package com.asiainfo.billing.drquery.datasource;

import com.asiainfo.billing.drquery.core.DRQueryNestedRuntimeException;

public class DSRuntimeException extends DRQueryNestedRuntimeException{

	private static final long serialVersionUID = 3387683939925503493L;
	public DSRuntimeException(String msg) {
		super(msg);
	}
	public DSRuntimeException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
