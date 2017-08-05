package com.asiainfo.billing.drquery.process.core;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.billing.drquery.datasource.BaseDataSource;
import com.asiainfo.billing.drquery.datasource.DataSourceAdapter;
import com.asiainfo.billing.drquery.process.ProcessException;

/**
 * 
 * @author Rex Wong
 *
 * @version
 */
public abstract class DataSourceRoute {
	protected final static Log log = LogFactory.getLog(DataSourceRoute.class);
	protected DataSourceAdapter adapter;

	public void setAdapter(DataSourceAdapter adapter) {
		this.adapter = adapter;
	}
	/**
	 * 数据源路由，包含默认数据源逻辑
	 * 
	 * @param time
	 * @return
	 * @throws ProcessException
	 */
	public final BaseDataSource getDataSourceByTime(String time, String dbType) throws ProcessException{
		BaseDataSource dataSource = null;
		if(StringUtils.isNotEmpty(dbType)){
			dataSource=adapter.getDataSource(dbType);
			if(dataSource==null){
				if(log.isErrorEnabled()){
					log.error("defalutDataSource["+dbType+"] get find in Spring context,do you define it?");
				}
				throw new ProcessException("datasource["+dbType+"] is null,check app_process.xml");
			}
			return dataSource;
		}
		return getDataSource(time);
	}
	public abstract BaseDataSource getDataSource(String time) throws ProcessException;
}
