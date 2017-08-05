package com.asiainfo.billing.drquery.process.compile;

import com.asiainfo.billing.drquery.process.ProcessException;
import net.sf.cglib.reflect.FastMethod;

import com.asiainfo.billing.drquery.cache.ICache;
import com.asiainfo.billing.drquery.utils.ServiceLocator;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;

public class BeanInvoker {
	
	/** 缓存FastMethod **/
	private static ICache cache = ServiceLocator.getInstance().getService("localCache", ICache.class);

	@SuppressWarnings("rawtypes")
	public static Object invoke(String beanId, String methodName, Object[] params) throws ProcessException {
		Object instance = BeanHolder.getInstance(beanId);
		if(null == instance){
			throw new RuntimeException("Get instance[" + beanId + "] is null, please check the bean.xml to see whether the beanId is right or not!");
		}
	
		String methodKey = instance.getClass().getName() + "." + methodName;

		FastMethod fastMethod = cache.getValue(methodKey);
        if(fastMethod == null) {
            throw new ProcessException("method: " + methodKey + " not found for beanId: " + beanId);
        }
        Object value;
        try {
            value = fastMethod.invoke(instance, params);
        } catch (InvocationTargetException e) {
            throw new ProcessException(e.getMessage(), e.getTargetException());
        } catch (Exception e) {
            throw new ProcessException(e.getMessage(), e);
        }
		
		return value;
	}
}