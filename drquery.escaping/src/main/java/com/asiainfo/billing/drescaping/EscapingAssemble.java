package com.asiainfo.billing.drescaping;

import com.asiainfo.billing.drescaping.util.EscapingUtil;
import com.asiainfo.billing.drescaping.util.TripleDes;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 *
 * @author zhouquan3
 */
public class EscapingAssemble{
    private final static Log log = LogFactory.getLog(EscapingAssemble.class);
    private static Log monitor_log = LogFactory.getLog("monitor");

    public List<EscapingProcess> getEscapingProcessList(){        
        String busiType=PropertiesUtil.getProperty("busiType");
        String[] busiTypes=busiType.split(",");
        List<EscapingProcess> processList=new ArrayList<EscapingProcess>();
        for(int i=0;i<busiTypes.length;i++){
            processList.add(getEscapingProcess(busiTypes[i]));
        }  
        return processList;
    }
    
    private EscapingProcess getEscapingProcess(String busiType){
        int i=0;
        EscapingProcess process=new EscapingProcess();
        List<DBExtractBean> extracterList=new ArrayList<DBExtractBean>();
        String mapKey=PropertiesUtil.getProperty(busiType+"."+i);
        while(!("".equals(PropertiesUtil.getProperty(busiType+"."+i, "")))){
            String username=PropertiesUtil.getProperty(busiType+"."+i+".username","");
            String password=PropertiesUtil.getProperty(busiType+"."+i+".password","");
            String url=PropertiesUtil.getProperty(busiType+"."+i+".url","");
            String passwordDecrypt=getDecryptString(password);
            if("".equals(passwordDecrypt)){
                log.error("Check password Decrypt. Decrypt password file ["+password+"] failed.");
                monitor_log.error(EscapingUtil.getLocalHostIP()+","+"Check password Decrypt. Decrypt password file ["+password+"] failed.");
            }
            String driverClass=PropertiesUtil.getProperty(busiType+"."+i+".driverClass","");
            String querySQL=PropertiesUtil.getProperty(busiType+"."+i+".sql","");
            
            if("".equals(username)||"".equals(driverClass)
                    ||"".equals(querySQL)||"".equals(url)){
                log.error("Check runtime.properties. ["+busiType+"."+i+"]");
                monitor_log.error(EscapingUtil.getLocalHostIP()+","+"Check ["+busiType+"."+i+"] DB Config in runtime.properties.");
                i++;
                continue;
            }
                extracterList.add(getDBExtractBean(url, username, passwordDecrypt, driverClass, querySQL));
                i++;         
        }
        process.setExtractorList(extracterList);
        process.setMapKey(mapKey);
        return process;
    }
    
    private DBExtractBean getDBExtractBean(String url,String username,
            String password,String driverClass,String querySQL){
        DBBean dBBean=new DBBean();
        dBBean.setDriverClass(driverClass);
        dBBean.setPassword(password);
        dBBean.setUsername(username);
        dBBean.setUrl(url);
        
        DBExtractBean dBExtractBean=new DBExtractBean();
        dBExtractBean.setDbBean(dBBean);
        dBExtractBean.setQuerySql(querySQL);
        
        return dBExtractBean;
    }
    
    private String getDecryptString(String str){
        String result=TripleDes.doDecrypt(str);
        return result;
    }
}
