package com.asiainfo.billing.drescaping;

import com.asiainfo.billing.drescaping.util.EscapingUtil;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author zhouquan3
 */
public class DBBean {
    private final static Log log = LogFactory.getLog(DBBean.class);
    private static Log monitor_log = LogFactory.getLog("monitor");
    
    private String url;
    private String password;
    private String driverClass;
    private String username;

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public Connection getConnection(){
        try {
            Class.forName(driverClass).newInstance();
            Connection conn = DriverManager.getConnection(url, username, password);
            return conn;
        } catch (InstantiationException e) {
            e.printStackTrace();
            log.error(e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            log.error(e);
        } catch (ClassNotFoundException e) {
            log.error(e);
            monitor_log.error(EscapingUtil.getLocalHostIP()+","+"JDBC Class not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            log.error(e);
            monitor_log.error(EscapingUtil.getLocalHostIP()+","+"Create Connection ["+url+"] failed");
            e.printStackTrace();
        }
        log.error("Fail to create JDBC Connection from ["+url+"].");
        return null;
    }
    
}
