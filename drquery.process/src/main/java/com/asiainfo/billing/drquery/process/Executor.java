package com.asiainfo.billing.drquery.process;

import com.ailk.oci.ocnosql.common.exception.ClientRuntimeException;
import com.ailk.oci.ocnosql.common.util.CommonConstants;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by wangkai8 on 15/5/9.
 */
public class Executor {

    private final static Log log = LogFactory.getLog(Executor.class);

    private static Properties confProperty;

    private Configuration conf;

    private ThreadPoolExecutor threadPool;

    private static volatile Executor INSTANCE = null;
    /**
     * 可选的参数
     */

    private String msg;

    public static Executor getInstance()  {
        synchronized (Executor.class) {
            if (INSTANCE == null) {
                INSTANCE = new Executor();
            }
        }
        return INSTANCE;
    }
    private Executor() {

        readConf(); //获取客户端配置文件
        init(); //创建执行线程池，初始化系统参数
    }

    /**
     * 创建执行线程池，初始化系统参数
     */
    protected void init() {

        int corePoolSize = Integer.parseInt(get(
                CommonConstants.THREADPOOL_COREPOOLSIZE, "50"));// 默认并发运行的线程数
        int maximumPoolSize = Integer.parseInt(get(
                CommonConstants.THREADPOOL_MAXPOOLSIZE, "80"));// 默认池中最大线程数
        long keepAliveTime = Integer.parseInt(get(
                CommonConstants.THREADPOOL_KEEPALIVETIME, "60"));// 默认池中线程最大等待时间

        log.warn("[ocnosql]create default threadPool,corePoolSize="
                + corePoolSize + " maximumPoolSize=" + maximumPoolSize
                + " keepAliveTime=" + keepAliveTime + "s.");

        //创建执行线程池
        threadPool = new ThreadPoolExecutor(
                corePoolSize, // 并发运行的线程数
                maximumPoolSize, // 池中最大线程数
                keepAliveTime, // 池中线程最大等待时间
                TimeUnit.SECONDS, // 池中线程最大等待时间单位
                new LinkedBlockingQueue<Runnable>(), // 池中队列维持对象
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy() // 超过最大线程数方法，此处为自动重新调用execute方法
        );

        log.debug("[ocnosql]create default threadPool success.");

        //TableConfiguration.getInstance().readTableConfiguration(conf);
    }




    public static String get(String key, String defaultValue) {
        if (confProperty == null) {
            readConf();
        }
        String value = confProperty.getProperty(key);
        if (StringUtils.isEmpty(value)) {
            return defaultValue;
        }
        return value;
    }
    public Map<String,String> retrieveValueByPrefix(String prefix) {
        if (confProperty == null) {
            readConf();
        }
        Map<String,String> propertyValue = new HashMap<String,String>();
        Set<String> prpertyNames = confProperty.stringPropertyNames();
        for(String key:prpertyNames){
            if(key.startsWith(prefix)){
                propertyValue.put(key,confProperty.getProperty(key));
            }
        }
        return propertyValue;
    }

    /**
     * 获取客户端配置文件
     */
    private static void readConf() {
        log.info("[ocnosql]start read conf file " + CommonConstants.FILE_NAME + ".");
        FileInputStream fis = null;
        try {
            URL url = Executor.class.getClassLoader().getResource(CommonConstants.FILE_NAME);
            log.info("[ocnosql]start read conf file " + CommonConstants.FILE_NAME + "["+url+"].");
            InputStream in = Executor.class.getClassLoader()
                    .getResourceAsStream(CommonConstants.FILE_NAME);
            if (in == null) {
                throw new ClientRuntimeException("plz check if file "
                        + CommonConstants.FILE_NAME + " in classpath!");
            }
            confProperty = new Properties();
            confProperty.load(in);
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            try {
                if (fis != null)
                    fis.close();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
            log.info("[ocnosql]end read conf file " + CommonConstants.FILE_NAME + ".");
        }
    }

    public Configuration getConf() {
        return conf;
    }

    public ThreadPoolExecutor getThreadPool() {
        return threadPool;
    }
}
