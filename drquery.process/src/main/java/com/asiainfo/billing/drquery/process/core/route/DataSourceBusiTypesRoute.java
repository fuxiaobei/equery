package com.asiainfo.billing.drquery.process.core.route;

import com.asiainfo.billing.drquery.datasource.BaseDataSource;
import com.asiainfo.billing.drquery.process.ProcessException;
import com.asiainfo.billing.drquery.process.core.DataSourceRoute;
import com.asiainfo.billing.drquery.utils.DateUtil;

/**
 * 业务类型查询路由策略
 * @author Rex Wong
 *
 * @version
 */
public class DataSourceBusiTypesRoute extends DataSourceRoute{

	public BaseDataSource getDataSource(String time) throws ProcessException {
		BaseDataSource dataSource = null;
		if(time.equals(DateUtil.getCurrentMonth())||time.equals(DateUtil.getLastMonth())){
			dataSource=adapter.getDataSource("ocnosqlDataSource");
			if(dataSource==null){
				throw new ProcessException("datasource[ocnosqlDataSource] is null,check app_process.xml");
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
