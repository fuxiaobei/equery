package com.asiainfo.billing.drescaping.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 *
 * @author zhouquan3
 */
public class EscapingUtil {
    private final static Log log = LogFactory.getLog(EscapingUtil.class);
    private static Log monitor_log = LogFactory.getLog("monitor");
    
    public static String getLocalHostIP(){
        try {
            InetAddress inetAddress=InetAddress.getLocalHost();
            return inetAddress.getHostAddress();
        } catch (UnknownHostException ex) {
            log.equals("Get localhost IP failed. ,"+ex);
        }
        return null;
    }
    
}
