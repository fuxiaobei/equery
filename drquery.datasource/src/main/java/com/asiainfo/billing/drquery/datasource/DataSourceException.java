package com.asiainfo.billing.drquery.datasource;

import com.asiainfo.billing.drquery.core.DRQueryNestedCheckedException;

/**
 *
 * @author tianyi
 */
public class DataSourceException extends DRQueryNestedCheckedException {

	public DataSourceException(String msg) {
		super(msg);
	}
	public DataSourceException(String msg, Throwable cause) {
		super(msg, cause);
	}

	private static final long serialVersionUID = 1525741134423939700L;

}
