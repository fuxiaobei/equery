package com.asiainfo.billing.drquery.datasource;

import com.asiainfo.billing.drquery.datasource.query.DRQueryParameters;

import java.util.List;
import java.util.Map;

/**
 * @author tianyi
 */
public interface BaseDataSource {
	
	/**
	 * 数据查询
	 * 
	 * @param queryParameters
	 * @return
	 * @throws DataSourceException
	 */
	List<Map<String,String>> loadDR(DRQueryParameters queryParameters) throws DataSourceException;

    //<V> V loadDR(DRQueryParameters queryParameters) throws DataSourceException;

    /**
	 * 数据查询  通过sql语句查询
	 *
	 * @param sql
	 * @param args
	 * @return
	 * @throws DataSourceException
	 */
//	List<Map<String,String>> loadDR(String sql,Object[] args) throws DataSourceException;

//	/**
//	 * 用户业务类型查询
//	 * 
//	 * @param queryParameters
//	 * @return
//	 * @throws DataSourceException
//	 */
//	Map<String,String> queryTypes(DRQueryBusiTypes queryParameters) throws DataSourceException;
//        
//        /**
//	 * 清单查询
//	 * 
//	 * @param queryParameters
//	 * @return
//	 * @throws DataSourceException
//	 */
//	List<Map<String,String>> queryCheck(DRQueryParameters queryParameters,MetaModel metaModel) throws DataSourceException;
	
}
