package com.asiainfo.billing.drquery.connection.pool;

import com.asiainfo.billing.drquery.core.DRQueryNestedRuntimeException;

public class PoolException extends DRQueryNestedRuntimeException{

	private static final long serialVersionUID = 1788283201197149689L;

	public PoolException(String msg) {
		super(msg);
	}
	public PoolException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
