package com.asiainfo.billing.drquery.datasource.ocnosql;

import com.asiainfo.billing.drquery.connection.ConnectionException;
import com.asiainfo.billing.drquery.connection.ConnectionHolder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class OCNoSqlConnectionHolder implements ConnectionHolder{
	private final static Log log = LogFactory.getLog(OCNoSqlConnectionHolder.class);
    
	public Object getNatvieConnection() throws ConnectionException {

		return null;
	}
    
	public void close() throws ConnectionException {
	}

	public void destroyObject() {

	}

	public boolean isConnected() throws ConnectionException {
		return true;
	}

	public void makeConnection() throws ConnectionException {

	}

}
