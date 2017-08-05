package com.asiainfo.billing.drquery.utils;

import com.asiainfo.billing.drquery.core.DRQueryNestedRuntimeException;

public class UtilsNestedException extends DRQueryNestedRuntimeException {

	private static final long serialVersionUID = -3519170502123462648L;

	public UtilsNestedException(String msg, Throwable cause) {
		super(msg, cause);
	}
	public UtilsNestedException(String msg) {
		super(msg);
	}
}
