package com.asiainfo.billing.drquery.client.distributed;

public class ZookeeperException extends RuntimeException{
	
	public ZookeeperException(String msg) {
		super(msg);
	}
	
	public ZookeeperException(Throwable e) {
		super(e);
	}
	
	public ZookeeperException(String msg, Throwable e) {
		super(msg, e);
	}
}
