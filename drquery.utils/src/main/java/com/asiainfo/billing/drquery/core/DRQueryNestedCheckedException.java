package com.asiainfo.billing.drquery.core;

import org.springframework.core.NestedCheckedException;

/**
 * 详单查询内部异常处理，目前是基于spring的异常进行重构
 * @author Rex Wong
 *
 * @version
 */
public class DRQueryNestedCheckedException extends NestedCheckedException{

	private static final long serialVersionUID = -1506157965822780889L;

	public DRQueryNestedCheckedException(String msg) {
		super(msg);
	}
	public DRQueryNestedCheckedException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
