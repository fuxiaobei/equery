package com.asiainfo.billing.drquery.cache;


public class CacheException extends RuntimeException {

	private static final long serialVersionUID = -1136652798904348482L;

	public CacheException(String msg) {
		super(msg);
	}

	public CacheException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public CacheException(Throwable cause) {
		super(cause != null ? cause.getMessage() : null, cause);
	}
	@Override
	public String getMessage() {
		return buildMessage(super.getMessage(), getCause());
	}
	public String getErrorCode() {
		Throwable cause = getCause();
		if (cause instanceof CacheException) {
			return ((CacheException) cause).getErrorCode();
		}
		return null;
	}
	public static String buildMessage(String message, Throwable cause) {
		if (cause != null) {
			StringBuilder sb = new StringBuilder();
			if (message != null) {
				sb.append(message).append("; ");
			}
			sb.append("nested excption: ").append(cause);
			return sb.toString();
		}
		else {
			return message;
		}
	}
}
