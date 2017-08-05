package com.asiainfo.billing.drquery.exception;

public interface IExceptionHandler {
	
	public final String LOG_INFO = "info";
	public final String LOG_DEBUG = "debug";
	public final String LOG_ERROR = "error";
	public final String LOG_WARN = "warn";
	public final String LOG_FATAL = "fatal";
	public final String LOG_NO = "no";

	public ExceptionMessage handlerException(BaseException e);
}
