package com.asiainfo.billing.drquery.connection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 连接工具类
 * 
 * @author Rex Wong
 *
 * @version
 */
@SuppressWarnings("rawtypes")
public class ConnectionUtils {
	
	private final static Log log = LogFactory.getLog(ConnectionUtils.class);
	
	/**
	 * 从连接工厂中取得一个连接
	 * @param factory
	 * @return
	 * @throws ConnectionException 
	 */
	public static Object getConnection(ConnectionFactory factory) throws ConnectionException {
		return doGetConnection(factory);
	}
	
	public static Object doGetConnection(ConnectionFactory factory) throws ConnectionException {
		return factory.getConnectionHolder().getNatvieConnection();
	}
	@SuppressWarnings("unchecked")
	public static void releaseConnection(ConnectionHolder holder, ConnectionFactory factory) 
			throws ConnectionException {
		if (holder == null) {
			factory.getPool().destroy();
			return;
		}
		//TODO 是否需要关闭连接？
		Object con = holder.getNatvieConnection();
		factory.getPool().returnResource(holder);
	}
}
