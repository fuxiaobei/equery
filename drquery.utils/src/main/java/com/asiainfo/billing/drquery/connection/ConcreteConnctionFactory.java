package com.asiainfo.billing.drquery.connection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.asiainfo.billing.drquery.connection.pool.ConcretePool;
import com.asiainfo.billing.drquery.connection.pool.Pool;
import com.asiainfo.billing.drquery.connection.pool.config.PoolConfig;

public class ConcreteConnctionFactory<T extends ConnectionHolder> implements InitializingBean,DisposableBean,ConnectionFactory<T> {
	
	private final static Log log = LogFactory.getLog(ConcreteConnctionFactory.class);
	private boolean usePool = true;
	private Pool<T> pool = null;
    private String connectionType;
	public void setConnectionType(String connectionType) {
		this.connectionType = connectionType;
	}

	PoolConfig poolConfig = new PoolConfig();
	
	public void setUsePool(boolean usePool) {
		this.usePool = usePool;
	}

	public void setPoolConfig(PoolConfig poolConfig) {
		this.poolConfig = poolConfig;
	}

	public T getConnectionHolder() throws ConnectionException {
		T con = fetchConnector();
		return con;
	}
	protected T fetchConnector() throws ConnectionException {
		try {
			if (usePool && pool != null) {
				return pool.getResource();
			}
		} catch (Exception ex) {
			throw new ConnectionException("",ex);
		}
		return null;
	}
	
	public Pool<T> getPool() {
		return pool;
	}

	public void afterPropertiesSet() {
		pool = new ConcretePool<T>(poolConfig,connectionType);
	}

	public void destroy() throws Exception {
		if (usePool && pool != null) {
			try {
				pool.destroy();
			} catch (Exception ex) {
				log.warn("Cannot properly close Connection pool", ex);
			}
			pool = null;
		}
		
	}

}
