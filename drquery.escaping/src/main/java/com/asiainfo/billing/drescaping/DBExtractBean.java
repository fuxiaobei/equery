package com.asiainfo.billing.drescaping;

import com.asiainfo.billing.drescaping.util.EscapingUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.*;
import java.util.*;

/**
 *
 * @author zhouquan3
 */
public class DBExtractBean {

    private final static Log log = LogFactory.getLog(DBExtractBean.class);
    private static Log monitor_log = LogFactory.getLog("monitor");
    private DBBean dbBean;
    private String querySql;

    public void setDbBean(DBBean dbBean) {
        this.dbBean = dbBean;
    }

    public void setQuerySql(String querySql) {
        this.querySql = querySql;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Map<String, Object> getEscapingMap() {
        try {
            Connection conn = dbBean.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs;
            rs = stm.executeQuery(querySql);
            Map<String, Object> escapingMap = new HashMap<String, Object>();
            ResultSetMetaData meta = null;
            meta = rs.getMetaData();
            while (rs.next()) {
                List resultList = new ArrayList();
                int columnCount = meta.getColumnCount();
                if(columnCount >= 2){
                	String key = rs.getString(1);
                	Map record = new LinkedHashMap();
                	for(int i = 0; i < columnCount; i++){
                		record.put(meta.getColumnName(i+1), rs.getString(i+1));
                	}
                	if(escapingMap.containsKey(key)){
                		((List)escapingMap.get(key)).add(record);
                	}else{
                		resultList.add(record);
                		escapingMap.put(key, resultList);
                	}
                	System.out.println(record);
                }else{
                 log.error("Result Set Column less than 2 columns.");
              }
            }
            rs.close();
            stm.close();
            conn.close();
            return escapingMap;
        } catch (SQLException ex) {
            log.error(ex + " sql [" + querySql + "]");
            monitor_log.error(EscapingUtil.getLocalHostIP() + "," + "Query DB failed. sql [" + querySql + "]");
        }
        return null;
    }
}
