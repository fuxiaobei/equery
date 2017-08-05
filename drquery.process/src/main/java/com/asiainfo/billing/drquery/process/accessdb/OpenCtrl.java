package com.asiainfo.billing.drquery.process.accessdb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.billing.drquery.cache.ICache;
import com.asiainfo.billing.drquery.connection.ConnectionException;
import com.asiainfo.billing.drquery.connection.DRConnection;

public class OpenCtrl {
	private String ctrlTable;
	private static final String key="OPEN_CTRL";
	
	class OpenTimeTypr{
		static final String CDR="1"; //详单开放时间
		static final String BILL="2"; //账单开放时间
		static final String GROUP_HIS_BILL="3"; //集团历史账单
		static final String BILL_TEST ="4";	//账单测试开放时间
	}
	
	private final static Log log = LogFactory.getLog(OpenCtrl.class);	
	protected ICache localCache;
	
	public void setCtrlTable(String ctrlTable) {
		this.ctrlTable = ctrlTable;
	}
	
	@Resource
	public void setLocalCache(ICache localCache) {
		this.localCache = localCache;
	}

	public String getCdrOpenTime(){
		Map<String,String> map= localCache.getValue(key);
		if(map == null){
			map = qryOpenTime();
		}
		String strDayHour = map.get(OpenTimeTypr.CDR);
		if(strDayHour == null || "".equals(strDayHour)){
			strDayHour =  "0308";
			log.debug( "读取详单开放时间失败，使用默认设置0308");
		}
		return strDayHour;
	}
	
	public String getOpenTime(String openType){
		Map<String,String> map= localCache.getValue(key);
		if(map == null){
			map = qryOpenTime();
		}
		return map.get(openType);
	}
	
	private Map<String, String> qryOpenTime(){
		DRConnection drConnection = new DRConnection("CSQRYConnectionFactory");
		Statement st = null;
		ResultSet  rs = null;
		try {
			String sql = "SELECT QRY_TYPE,LPAD(OPEN_DAY, 2, 0) OPEN_DAY,LPAD(OPER_Hour, 2, 0) OPEN_HOUR FROM " + ctrlTable;
			Connection conn = (Connection) drConnection.getConnectionFromFactory();
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			Map<String,String> map =new HashMap<String,String>();
			while(rs.next()){
				map.put(rs.getString("QRY_TYPE"),rs.getString("OPEN_DAY")+rs.getString("OPEN_HOUR"));
			}
			localCache.putData2Cache(key, map, -1);
			return map;
		}  catch (ConnectionException e) {
			log.error(e.getMessage(), e);
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
		}finally{
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					log.error(e);
				}
			}
			if(st != null){
				try {
					st.close();
				} catch (SQLException e) {
					log.error(e);
				}
			}
			drConnection.releaseConnection();
		}
		return null;
	}
}
