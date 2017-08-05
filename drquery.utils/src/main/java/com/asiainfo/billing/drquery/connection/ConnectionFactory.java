package com.asiainfo.billing.drquery.connection;

import com.asiainfo.billing.drquery.connection.pool.Pool;


/**
 * @author Rex Wong
 *
 * @version
 */
public interface ConnectionFactory<T extends ConnectionHolder> {

	/**
	 * 返回具体连接持有者
	 * 
	 * @return
	 * @throws ConnectionException 
	 */
	T getConnectionHolder() throws ConnectionException;
	
	Pool<T> getPool();
	
}