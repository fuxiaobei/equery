package com.asiainfo.billing.drquery.process.core.route;

import com.asiainfo.billing.drquery.datasource.BaseDataSource;
import com.asiainfo.billing.drquery.process.ProcessException;
import com.asiainfo.billing.drquery.utils.DateUtil;

/**
 *
 * @author zhouquan3
 */
public class DataSourceQueryCheckRoute extends BaseDataSourceRoute{
    	public BaseDataSource getDataSource(String time) throws ProcessException {
		BaseDataSource dataSource = null;
		if(time.equals(DateUtil.getCurrentMonth())||time.equals(DateUtil.getLastMonth())){
			dataSource=adapter.getDataSource("ocnosqlDataSource");
			if(dataSource==null){
				throw new ProcessException("datasource[ocnosqlDataSource] is null,check app_process.xml");
			}
		}
		else{
			dataSource=adapter.getDataSource("gBDataSource");
			if(dataSource==null){
				throw new ProcessException("datasource[gBDataSource] is null,check app_process.xml");
			}
		}
		return dataSource;
	}
}
