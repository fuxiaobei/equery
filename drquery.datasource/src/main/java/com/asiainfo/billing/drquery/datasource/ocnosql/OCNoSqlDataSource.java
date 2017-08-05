package com.asiainfo.billing.drquery.datasource.ocnosql;

import com.ailk.oci.ocnosql.client.jdbc.HbaseJdbcHelper;
import com.ailk.oci.ocnosql.client.jdbc.phoenix.PhoenixJdbcHelper;
import com.asiainfo.billing.drquery.connection.ConnectionException;
import com.asiainfo.billing.drquery.connection.ConnectionFactory;
import com.asiainfo.billing.drquery.datasource.BaseDataSource;
import com.asiainfo.billing.drquery.datasource.DataSourceException;
import com.asiainfo.billing.drquery.datasource.query.DRQueryParameters;
import com.asiainfo.billing.drquery.datasource.query.DefaultQueryParams;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Rex Wong
 *
 * @version
 */
public class OCNoSqlDataSource implements BaseDataSource{
	
	private final static Log log = LogFactory.getLog(OCNoSqlDataSource.class);
	
	private ConnectionFactory<OCNoSqlConnectionHolder> factory;
	private String tablePrefix;//表明前缀
	
	public void setFactory(ConnectionFactory<OCNoSqlConnectionHolder> factory) {
		this.factory = factory;
	}
	
	public void setTablePrefix(String tablePrefix) {
		this.tablePrefix = tablePrefix;
	}


    @Override
	public List<Map<String, String>> loadDR(DRQueryParameters parameters) throws DataSourceException {
        DefaultQueryParams params = (DefaultQueryParams) parameters;
        log.info(params.getSql());
		List<Map<String, String>>  retArr = new ArrayList<Map<String, String>>();
        HbaseJdbcHelper jdbcHelper = null;
        try{
			long t1 = System.currentTimeMillis();
			jdbcHelper = new PhoenixJdbcHelper();
			ResultSet rsResultSet = jdbcHelper.executeQueryRaw(params.getSql(), params.getArgs());
            int columnCount = rsResultSet.getMetaData().getColumnCount();
            while (rsResultSet.next()) {
                Map<String, String> map = new HashMap<String, String>();
                for (int i = 1; i <= columnCount; i++) {
                    String metaData = rsResultSet.getMetaData().getColumnName(i);
                    Object valueData = rsResultSet.getObject(i);
                    if(valueData instanceof BigDecimal)
                        map.put(metaData, rsResultSet.getString(i).replace(",", ""));
                    else
                        map.put(metaData, rsResultSet.getString(i));
                }
                retArr.add(map);
            }
			log.info("ocnosql return " + retArr.size() + " records, query token: " + (System.currentTimeMillis() - t1) + "ms");
		} catch(Exception ex){
			Throwable cause = ex.getCause();
			if(cause!=null&&(cause instanceof SQLException)){
				throw new DataSourceException("ocnosql exec query sqlexception,error is ["+cause.getMessage()+"]");
			}else if(StringUtils.equals(ex.getMessage(), "hostname can't be null")){
				try {
					throw new ConnectionException("get ocnosql connection failed:do you set hostname in your os?",ex);
				}catch (ConnectionException e) {
					log.error(e);
				}
			}else{
				throw new DataSourceException("ocnosql found exception",ex);
			}
		}finally{
           try{
               if(jdbcHelper != null){
                  jdbcHelper.close();
               }
           } catch (SQLException e){
              throw new DataSourceException("close connection error", e);
           }
        }
		return retArr;
	}
}
