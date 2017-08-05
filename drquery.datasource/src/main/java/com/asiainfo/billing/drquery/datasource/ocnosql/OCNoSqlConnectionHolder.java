package com.asiainfo.billing.drquery.datasource.ocnosql;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ailk.oci.ocnosql.client.spi.ClientAdaptor;
import com.asiainfo.billing.drquery.connection.ConnectionException;
import com.asiainfo.billing.drquery.connection.ConnectionHolder;
import com.asiainfo.billing.drquery.utils.ServiceLocator;

public class OCNoSqlConnectionHolder implements ConnectionHolder{
	private final static Log log = LogFactory.getLog(OCNoSqlConnectionHolder.class);
    private ClientAdaptor client;
    
	public Object getNatvieConnection() throws ConnectionException {
		if(client!=null){
			return client;
		}
		makeConnection();
		return client;
	}
    
	public void close() throws ConnectionException {
	}

	public void destroyObject() {
		log.info("destroy instance of ocnosql connection from connection pool");
		client=null;
	}

	public boolean isConnected() throws ConnectionException {
		return true;
	}

	public void makeConnection() throws ConnectionException {
		log.info("make new instance of ocnosql connection");
		try {
			client = ServiceLocator.getInstance().getService("hbaseQuery", ClientAdaptor.class);
		}catch (Exception e){
			log.error(e);
			throw  new ConnectionException("make new instance of ocnosql connection error!",e);
		}
	}

}
