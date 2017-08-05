package com.asiainfo.billing.drescaping;

import com.asiainfo.billing.drescaping.util.EscapingUtil;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 *
 * @author zhouquan3
 */
public class PropertiesUtil {
    private final static Log log = LogFactory.getLog(PropertiesUtil.class);
    private static Log monitor_log = LogFactory.getLog("monitor");
    
    private static Properties properties;
    
    private static void init(){
        try {
            properties=new Properties();
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource resource = resolver.getResource("classpath:runtime.properties");
            properties.load(new FileReader(resource.getFile()));
        } catch (FileNotFoundException ex) {
            log.error("runtime.properties not found.");
            monitor_log.error(EscapingUtil.getLocalHostIP() + "," + "Escaping runtime.properties not found.");
        } catch (IOException ex) {
            log.error(ex);
            monitor_log.error(EscapingUtil.getLocalHostIP() + "," + "read Escaping runtime.properties failed.");
        }
    }
    
    public static String getProperty(String str){
        if(properties==null){
            init();
        }
        String temp=properties.getProperty(str);
        if("".equals(temp)||temp==null){
            log.error("Can not get property ["+str+"] from runtime.properties");
            monitor_log.error(EscapingUtil.getLocalHostIP() + "," + "Can not get property ["+str+"] from runtime.properties");
        }
        return temp; 
    }
    
    public static String getProperty(String str,String str2){
        if(properties==null){
            init();
        }
        return properties.getProperty(str,str2);
    }
    
}
