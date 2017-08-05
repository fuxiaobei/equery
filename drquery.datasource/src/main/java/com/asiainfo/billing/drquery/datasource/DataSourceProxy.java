package com.asiainfo.billing.drquery.datasource;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.billing.drquery.connection.ConnectionException;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class DataSourceProxy implements MethodInterceptor {
	private static final Log log = LogFactory.getLog(DataSourceProxy.class);
	private Object target;
	private ConcurrentMap<String,Long> exceptiontimeOut = new ConcurrentHashMap<String,Long>();
	private ConcurrentMap<String,Boolean> foundException = new ConcurrentHashMap<String,Boolean>();
	private ReentrantLock lock = new ReentrantLock();
	public boolean isFoundException(String dataSourceType) {
		return foundException.isEmpty()?false:foundException.get(dataSourceType);
	}
	private int timeout;
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	public Object getInstance(Object target) {
		if (isErrorTimeout(target.getClass().getName())) {
			foundException.put(target.getClass().getName(), false);
		}
		try{
			lock.lock();
			this.target = target;  
		}
		finally{
			lock.unlock();
		}
        Enhancer enhancer = new Enhancer();  
        enhancer.setSuperclass(this.target.getClass());  
        enhancer.setCallback(this);  
        return enhancer.create();  
    }  
	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		Object result = null;
		try{
			result = proxy.invoke(this.target, args);
		}
		catch(Exception e){
			if(e instanceof ConnectionException){
				foundException.put(target.getClass().getName(), true);
				exceptiontimeOut.put(this.target.getClass().getName(), System.currentTimeMillis());
			}
			else{
				Throwable ex = e.getCause();
				while(true){
					if(ex==null){
						break;
					}
					if(ex instanceof ConnectionException){
						foundException.put(target.getClass().getName(), true);
						exceptiontimeOut.put(this.target.getClass().getName(), System.currentTimeMillis());
						throw ex;
					}
					ex = ex.getCause();
				}
			}
			throw e;
		}
        return result;
	}
	/**
	 * 判断取backup数据源是否超时
	 * @return true：已超时
	 */
	private boolean isErrorTimeout(String dataSourceType){
		boolean isTimeout=false;
		if(foundException.isEmpty()||foundException.get(dataSourceType)==null||!foundException.get(dataSourceType)){
			return isTimeout;
		}
		long beginTime = exceptiontimeOut.get(dataSourceType);
		long endTime = System.currentTimeMillis(); 
		long interTime = endTime-beginTime;
		if(log.isInfoEnabled()){
			log.info("interTime="+interTime);
		}
		if(interTime>timeout){
			exceptiontimeOut.put(dataSourceType, 0L);
			isTimeout = true;
		}
		return isTimeout;
	}
}
