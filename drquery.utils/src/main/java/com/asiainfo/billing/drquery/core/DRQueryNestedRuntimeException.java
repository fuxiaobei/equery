package com.asiainfo.billing.drquery.core;

import org.springframework.core.NestedRuntimeException;

/**
 * 详单查询内部异常处理，目前是基于spring的异常进行重构
 * @author Rex Wong
 *
 * @version
 */
public class DRQueryNestedRuntimeException extends NestedRuntimeException{

	private static final long serialVersionUID = -6265397652642018237L;

	public DRQueryNestedRuntimeException(String msg, Throwable cause) {
		super(msg, cause);
	}
	public DRQueryNestedRuntimeException(String msg) {
		super(msg);
	}
}
