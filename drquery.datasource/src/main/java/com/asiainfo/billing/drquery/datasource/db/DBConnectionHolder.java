package com.asiainfo.billing.drquery.datasource.db;

import com.asiainfo.billing.drquery.connection.ConnectionException;
import com.asiainfo.billing.drquery.connection.ConnectionHolder;
import com.asiainfo.billing.drquery.connection.PasswordDecoder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionHolder implements ConnectionHolder,InitializingBean {

	private final static Log log = LogFactory.getLog(DBConnectionHolder.class);
    private String driverclass;
    private String url;
    private String username;
    private String password;
    private PasswordDecoder decoder;

	public void setDriverclass(String driverclass) {
		this.driverclass = driverclass;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDecoder(PasswordDecoder decoder) {
        this.decoder = decoder;
    }

    private Connection conn;

	public boolean isConnected() {
		try {
			return !conn.isClosed();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	public Connection getNatvieConnection() throws ConnectionException{
		if(conn == null){
			makeConnection();
		}
		return conn;
	}
    
	public void close()  throws ConnectionException{
		try {
			conn.close();
		} catch (SQLException e) {
			conn=null;
			new ConnectionException("db close connection exception",e);
		}
		conn=null;
	}

	public void destroyObject() {
		log.info("destroy instance of db connection from connection pool");
		try {
			close();
		} catch (ConnectionException e) {
			new ConnectionException("db close connection exception",e);
		}
	}
	
	public void makeConnection() throws ConnectionException {
		if(conn!=null){
			return;
		}
		log.info("make new instance of db connection into connection pool");
		try {
			Class.forName(driverclass);
		} catch (ClassNotFoundException e) {
			log.error("db connection exception:url="+url, e);
			throw new ConnectionException("db connection exception",e);
		}

		try {
			conn = DriverManager.getConnection(url, username, decoder == null ? password : decoder.getPassword(password));
		} catch (SQLException e) {
			log.error("db connection exception:url="+url, e);
			throw new ConnectionException("db connection exception:url="+url, e);
		}
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		// do nothing
	}
}
