package com.asiainfo.billing.drquery.connection;


/**
 * 连接持有者，该接口实现类将持有一个连接，最终将该持有者放入连接池
 * </p>
 * 通过实现持有者接口，统一化连接方法，以备连接池使用
 * 
 * @author Rex Wong
 *
 * @version 0.1
 */
public interface ConnectionHolder {

	
	/**
	 * 得到一个新的连接
	 * @return
	 * @throws ConnectionException 
	 */
    Object getNatvieConnection() throws ConnectionException;
    
	void makeConnection() throws ConnectionException;
	/**
	 * 关闭连接
	 * @throws ConnectionException 
	 */
	void close() throws ConnectionException;
	/**
	 * 销毁连接池中该连接
	 */
	void destroyObject();
	
	/**
	 * 判断该连接是否正常
	 * @return
	 * @throws ConnectionException 
	 */
	boolean isConnected() throws ConnectionException;
	
	
}
