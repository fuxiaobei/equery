package com.asiainfo.billing.drquery;

import com.asiainfo.billing.drquery.core.DRQueryNestedRuntimeException;

public class ModelRuntimeException extends DRQueryNestedRuntimeException{

	private static final long serialVersionUID = -1321446247887453347L;
	
	public ModelRuntimeException(String msg) {
		super(msg);
	}
	public ModelRuntimeException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
