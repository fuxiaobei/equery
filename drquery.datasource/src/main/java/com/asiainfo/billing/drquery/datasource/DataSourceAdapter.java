package com.asiainfo.billing.drquery.datasource;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.billing.drquery.utils.ServiceLocator;
public class DataSourceAdapter {
	protected final static Log log = LogFactory.getLog(DataSourceAdapter.class);
	private Map<String,BaseDataSource> dataSourceEntries;
	
	public void setDataSourceEntries(Map<String, BaseDataSource> dataSourceEntries) {
		this.dataSourceEntries = dataSourceEntries;
	}
	private DataSourceProxy proxy;
	private Map<String,String> backupMapping;
	public void setBackupMapping(Map<String, String> backupMapping) {
		this.backupMapping = backupMapping;
	}
	public void setProxy(DataSourceProxy proxy) {
		this.proxy = proxy;
	}
	@SuppressWarnings("unchecked")
	public <T extends BaseDataSource> T getDataSource(String dbType){
		BaseDataSource dataSource =dataSourceEntries.get(dbType);
		if(dataSource==null){
			if(log.isErrorEnabled()){
				log.info("we can't found datasource["+dbType+"],do you set this datasource in Spring context or this datasource name is error?");
				throw new DSRuntimeException("we can't found datasource["+dbType+"],do you set this datasource in Spring context or this datasource name is error?");
			}
		}
		Object proxyDataSource = proxy.getInstance(dataSource);
		String dataSourceName = dataSource.getClass().getName();
		if(proxy.isFoundException(dataSourceName) && !backupMapping.isEmpty()){
			String backupDatasourceName = backupMapping.get(dbType);
			proxyDataSource = (BaseDataSource) ServiceLocator.getInstance().getService(backupDatasourceName); 
			if(log.isInfoEnabled()){
				log.info(dataSourceName+" found ConnectionException,so we extract backup datasource["+backupDatasourceName+"]");
			}			
		}
		return (T) proxyDataSource;
	}
	@SuppressWarnings("unchecked")
	public <T extends BaseDataSource> T getBackupDataSource(String dbType) {
		BaseDataSource dataSource =dataSourceEntries.get(dbType);
		return (T) dataSource;
	}
}
