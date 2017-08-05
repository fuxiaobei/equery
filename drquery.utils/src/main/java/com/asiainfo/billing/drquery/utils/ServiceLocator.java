package com.asiainfo.billing.drquery.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

public class ServiceLocator implements BeanFactoryAware{

	private static final Log logger = LogFactory.getLog(ServiceLocator.class);

	private static ServiceLocator serviceLocator = null;

	public static BeanFactory beanFactory;

	public static ServiceLocator getInstance() {
		if (serviceLocator == null) {
			logger.debug(">>>start init spring application.");
			synchronized (ServiceLocator.class) {
				try{
					serviceLocator = beanFactory.getBean("serviceLocator", ServiceLocator.class);
		    	}
		    	catch(BeansException ex){
		    		new UtilsNestedException("Nested Exception:",ex);
		    	}
			}
		}
		return serviceLocator;
	}
    public <T> T getService(String beanName,Class<T> requiredType){
    	try{
    		return beanFactory.getBean(beanName, requiredType);
    	}
    	catch(BeansException ex){
    		new UtilsNestedException("Nested Exception:",ex);
    	}
    	return null;
    }
    public Object getService(String beanName){
    	try{
    		return beanFactory.getBean(beanName);
    	}
    	catch(BeansException ex){
    		new UtilsNestedException("Nested Exception:",ex);
    	}
    	return null;
    }
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		ServiceLocator.beanFactory = beanFactory;
	}
}
