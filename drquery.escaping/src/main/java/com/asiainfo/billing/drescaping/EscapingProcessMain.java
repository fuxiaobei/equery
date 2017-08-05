package com.asiainfo.billing.drescaping;

import com.asiainfo.billing.drescaping.redis.RedisUtil;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author zhouquan3
 */
public class EscapingProcessMain {
    private final static Log log = LogFactory.getLog(EscapingProcessMain.class);
    private static Log monitor_log = LogFactory.getLog("monitor");
    
    private List<EscapingProcess> processList;
    private RedisUtil redisUtil;
    private long mapSize=10000;
    boolean defaultMapSize=true;
    
    private static EscapingProcessMain main;
    
    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    public void setMapSize(long mapSize) {
        this.mapSize = mapSize;
        defaultMapSize=false;
    }
    
    public void run(){
        EscapingAssemble ea=new EscapingAssemble();
        processList=ea.getEscapingProcessList();
    
        for(int i=0;i<processList.size();i++){
            EscapingProcess process=processList.get(i);         
            process.setRedisUtil(redisUtil);
            if(defaultMapSize){
                log.debug("Use default Map Size 10000");
            }
            process.setMapSize(mapSize);
            process.run();
        }
    
    }
    
    public static void main(String[] args){
        ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(new String[] { "spring/applicationContext.xml"});
        main=appContext.getBean("main", EscapingProcessMain.class);
        main.run();
        log.debug("All done");
//        EscapingProcessMain ep=new EscapingProcessMain();
//        ep.run();
        
    }
    
}
