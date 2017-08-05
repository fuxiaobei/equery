package com.asiainfo.billing.drquery.datasource.db;

import com.asiainfo.billing.drquery.connection.ConnectionException;
import com.asiainfo.billing.drquery.connection.DRConnection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by wangkai8 on 15/6/8.
 */
public class BaseDAO {

    public final static Log log = LogFactory.getLog(BaseDAO.class);

    private DRConnection drConnection;
    private String factoryName;

    public BaseDAO(String factoryName) {
        this.factoryName = factoryName;
        drConnection = new DRConnection(factoryName);
    }

    interface Callback {
        Object getRecord(ResultSet rs) throws SQLException;
    }


    public List queryBySQL(String sql, Object[] args, Callback callback) throws ConnectionException {
        List list = new ArrayList();
        Connection conn = (Connection) drConnection.getConnectionFromFactory();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            if(args != null) {
                for (int i = 0; i < args.length; i++) {
                    ps.setObject(i + 1, args[i]);
                }
            }
            rs = ps.executeQuery();
            while(rs.next()) {
                list.add(callback.getRecord(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("execute sql exception", e);
        } finally {
            if(rs != null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    log.error(e);
                }
            }
            if(ps != null){
                try {
                    ps.close();
                } catch (SQLException e) {
                    log.error(e);
                }
            }
            drConnection.releaseConnection();
        }
    }


    public List<Map> queryBySQL(String sql, Object[] args) throws ConnectionException {
        return (List<Map>) queryBySQL(sql, args, new Callback() {
            @Override
            public Object getRecord(ResultSet rs) throws SQLException{
                int count = rs.getMetaData().getColumnCount();
                Map<Object, Object> record = new LinkedHashMap<Object, Object>();
                for(int i = 0; i < count; i++) {
                    record.put(rs.getMetaData().getColumnName(i + 1), rs.getObject(i +1));
                }
                return record;
            }
        });
    }


    public List<Object[]> queryBySQLArr(String sql, Object[] args) throws ConnectionException {
        return (List<Object[]>) queryBySQL(sql, args, new Callback() {
            @Override
            public Object getRecord(ResultSet rs) throws SQLException{
                int count = rs.getMetaData().getColumnCount();
                Object[] record = new Object[count];
                for(int i = 0; i < count; i++) {
                    record[i] = rs.getObject(i +1);
                }
                return record;
            }
        });
    }


    public int update(String sql, Object[] args) throws ConnectionException {
        Connection conn = (Connection) drConnection.getConnectionFromFactory();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            if(args != null) {
                for (int i = 0; i < args.length; i++) {
                    ps.setObject(i + 1, args[i]);
                }
            }
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("execute sql exception", e);
        } finally {
            if(ps != null){
                try {
                    ps.close();
                } catch (SQLException e) {
                    log.error(e);
                }
            }
            drConnection.releaseConnection();
        }
    }
}
