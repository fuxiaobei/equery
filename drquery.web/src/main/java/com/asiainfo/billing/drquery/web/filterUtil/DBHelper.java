package com.asiainfo.billing.drquery.web.filterUtil;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.sql.RowSet;

public class DBHelper {

    private String driver;
    private String url;
    private String dbname;
    private String dbpass;

    public DBHelper(String driver, String url, String dbname, String dbpass) {
        super();
        this.driver = driver;
        this.url = url;
        this.dbname = dbname;
        this.dbpass = dbpass;
    }

    public Connection getConn() throws ClassNotFoundException, SQLException {

        Class.forName(driver);
        Connection conn = (Connection) DriverManager.getConnection(url, dbname, dbpass);
        return conn;
    }

    /**
     * 释放资源
     *
     * @param conn-连接对象
     * @param rs-结果集
     */
    public void closeAll(Connection conn, Statement ps, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 执行查询操作
     *
     * @param sql sql语句
     * @return RowSet 结果集，可直接使用
     */
    public Map executeQuery(String sql) throws SQLException, ClassNotFoundException, Exception {
        Connection connection = null;
        Statement state = null;
        ResultSet rs = null;
        List li = null;
        List types=null;
        Map map = new HashMap();
        try {
            connection = this.getConn();
            state = connection.createStatement();
            
             Logger.getLogger("sql: "+sql);
            long b=System.currentTimeMillis();
             Logger.getLogger("开始查询"+b);
             
            rs = state.executeQuery(sql);
            
            Logger.getLogger("共消耗: "+(System.currentTimeMillis()-b)+"ms");
            li = getRowSetColName(rs);
            types=getRowSetColType(rs);
            
            map.put("fields", li);
            List allRows = null;
            allRows = getRowSetData(rs, li,types);

            map.put("contents", allRows);
            map.put("totalCount", allRows.size());


        } catch (SQLException e) {
            throw e;
        } catch (ClassNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        } finally {
            closeAll(connection, state, rs);
        }
        return map;
    }
    
    
    public String executeQuerySql(String sql) throws SQLException, ClassNotFoundException, Exception {
    
        Connection connection = null;
        Statement state = null;
        ResultSet rs = null;
        String result="";
        
        try {
            connection = this.getConn();
            state = connection.createStatement();
            
             Logger.getLogger("sql: "+sql);
            long b=System.currentTimeMillis();
             Logger.getLogger("开始查询"+b);
              
            rs = state.executeQuery(sql);
            while(rs.next()){
                //result=rs.getObject("valid_date").toString();
                result=rs.getObject("valid_date")==null?"":rs.getObject("valid_date").toString();
            }
        }catch(Exception e){
            throw e;
        } finally {
            closeAll(connection, state, rs);
        }
        return result;
        
    
    }

    public int queryCount(String sql) throws SQLException, ClassNotFoundException, Exception {
        Connection connection = null;
        Statement state = null;
        ResultSet rs = null;
        int totalCount = 0;
        try {
            connection = this.getConn();
            state = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            rs = state.executeQuery(sql);
            System.out.println(rs);
            
            totalCount = rs.getRow();
            while(rs.next()){
               totalCount= rs.getInt("totalCount");
            }
        } catch (SQLException e) {
            throw e;
        } catch (ClassNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        } finally {
            closeAll(connection, state, rs);
        }
        return totalCount;
    }

    /**
     * 将ResultSet转换为RowSet对象
     *
     * @param rs 数据集
     * @return RowSet
     */
    public RowSet populate(ResultSet rs) throws SQLException {
//        CachedRowSetImpl crs = new CachedRowSetImpl();
//        crs.populate(rs);
//        return crs;
    	return null;
    }

    /**
     * 从RowSet中获取列头
     *
     * @param rs 数据集
     * @return List
     */
//    public List getRowSetColName(RowSet rs) throws SQLException {
//        ArrayList collist = new ArrayList();
//        for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
//            collist.add(rs.getMetaData().getColumnName(i + 1).toUpperCase());
//        }
//        return collist;
//    }
  
    public List getRowSetColName(ResultSet rs) throws SQLException {
        ArrayList collist = new ArrayList();
        for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
            collist.add(rs.getMetaData().getColumnName(i + 1).toUpperCase());
        }
        return collist;
    }
    
     public List getRowSetColType(ResultSet rs) throws SQLException {
        ArrayList colTypelist = new ArrayList();
        for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
            colTypelist.add(rs.getMetaData().getColumnTypeName(i + 1));
        }
        return colTypelist;
    }

     
     
     
     
     
     
     
     
    public List getRowSetData(ResultSet rs, List li,List types) throws SQLException {
        
        List allRows = new ArrayList();
        while (rs.next()) {
            List r = new ArrayList();
            for (int i = 0; i < li.size(); i++) {
               
                    if(types.get(i).equals("DATETIME")){
                        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        java.sql.Timestamp date=rs.getTimestamp(li.get(i).toString());
                        if(null!=date&&!"".equals(date)){
                            String time=format.format(date);
                            r.add(time);
                        }else{
                            r.add("");
                        }
                    }else{
                         r.add(rs.getObject(String.valueOf(li.get(i))) == null ? "" : rs.getObject(String.valueOf(li.get(i))));
                    
                    }
             
            }
            allRows.add(r);
        }
        return allRows;
    }
    
    
    
}
