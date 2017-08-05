package com.asiainfo.billing.drquery.connection;

import com.asiainfo.billing.drquery.utils.ServiceLocator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DRConnection {
	
	private final static Log log = LogFactory.getLog(DRConnection.class);

	private ConnectionFactory factory;
	
	private ConnectionHolder holder;
	
	private Object navtiveConnection;
	
	private String factoryName;

    public DRConnection(String factoryName) {
        this.factoryName = factoryName;
    }
	
	
	/**
	 * 根据连接类型获得Connection
	 * @return
	 * @throws ConnectionException
	 */
	public Object getConnectionFromFactory() throws ConnectionException{
		factory = ServiceLocator.getInstance().getService(factoryName, ConcreteConnctionFactory.class);
		try {
			holder = factory.getConnectionHolder();
			navtiveConnection = holder.getNatvieConnection();
		} catch (ConnectionException e) {
			throw new ConnectionException("get db connection failed", e);
		}
		return navtiveConnection;
	}
	
	
	/**
	 * 释放连接
	 */
	public void releaseConnection(){
		try {
			ConnectionUtils.releaseConnection(holder, factory);
		} catch (ConnectionException e) {
			log.error(e);
		}
	}


	public ConnectionFactory getFactory() {
		return factory;
	}


	public void setFactory(ConnectionFactory factory) {
		this.factory = factory;
	}


	public ConnectionHolder getHolder() {
		return holder;
	}


	public void setHolder(ConnectionHolder holder) {
		this.holder = holder;
	}
	
	
	
	
}
