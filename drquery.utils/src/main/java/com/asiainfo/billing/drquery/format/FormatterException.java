package com.asiainfo.billing.drquery.format;

import com.asiainfo.billing.drquery.core.DRQueryNestedRuntimeException;

public class FormatterException extends DRQueryNestedRuntimeException {
	private static final long serialVersionUID = 7348273609684334198L;
	public FormatterException(String msg, Throwable cause) {
		super(msg);
	}
	public FormatterException(String msg) {
		super(msg);
	}
}
