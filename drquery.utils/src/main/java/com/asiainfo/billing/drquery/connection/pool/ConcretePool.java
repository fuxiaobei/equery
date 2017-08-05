package com.asiainfo.billing.drquery.connection.pool;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool.Config;

import com.asiainfo.billing.drquery.connection.ConnectionException;
import com.asiainfo.billing.drquery.connection.ConnectionHolder;
import com.asiainfo.billing.drquery.core.DRQueryNestedRuntimeException;
import com.asiainfo.billing.drquery.utils.ServiceLocator;

public class ConcretePool<T> extends Pool<T> {
    public ConcretePool(final Config poolConfig,String connectionType) {
        super(poolConfig, new ConnctionFactory(connectionType));
    }
	/**
     * PoolableObjectFactory custom impl.
     */
    private static class ConnctionFactory extends BasePoolableObjectFactory {
    	private final static Log log = LogFactory.getLog(ConnctionFactory.class);
        private String connectionType;
        public ConnctionFactory(String connectionType) {
        	super();
        	this.connectionType = connectionType;
        }
        public Object makeObject() throws Exception {
        	log.debug("need make new collection");
        	ConnectionHolder dbConnectionholder = ServiceLocator.getInstance().getService(connectionType,ConnectionHolder.class);
        	if(dbConnectionholder==null){
        		throw new DRQueryNestedRuntimeException("cant find ConnectionHolder from Spring context: "+connectionType);
        	}
        	dbConnectionholder.makeConnection();
            return dbConnectionholder;
        }

        public void destroyObject(final Object obj) throws Exception {
            if (obj instanceof ConnectionHolder) {
                final ConnectionHolder con = (ConnectionHolder) obj;
                con.destroyObject();
            }
        }

        public boolean validateObject(final Object obj) {
            if (obj instanceof ConnectionHolder) {
            	 final ConnectionHolder con = (ConnectionHolder) obj;
            	 try {
					return con.isConnected();
				} catch (ConnectionException e) {
					e.printStackTrace();
				}
            } else {
                return false;
            }
			return false;
        }

    }

}
