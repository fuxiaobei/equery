package com.asiainfo.billing.drquery.process.core.route;

import com.asiainfo.billing.drquery.datasource.BaseDataSource;
import com.asiainfo.billing.drquery.process.ProcessException;
import com.asiainfo.billing.drquery.process.core.DataSourceRoute;
import com.asiainfo.billing.drquery.utils.DateUtil;

public class DataSourceRoute1 extends DataSourceRoute{

	public BaseDataSource getDataSource(String time) throws ProcessException {
		BaseDataSource dataSource = null;
		if(time.equals(DateUtil.getCurrentMonth())||time.equals(DateUtil.getLastMonth())){
			dataSource=adapter.getDataSource("prebillSource");
			if(dataSource==null){
				throw new ProcessException("datasource[prebillSource] is null,check app_process.xml");
			}
		}
		else{
			dataSource=adapter.getDataSource("ondemandDataSource");
			if(dataSource==null){
				throw new ProcessException("datasource[ondemandDataSource] is null,check app_process.xml");
			}
		}
		return dataSource;
	
	}

}
