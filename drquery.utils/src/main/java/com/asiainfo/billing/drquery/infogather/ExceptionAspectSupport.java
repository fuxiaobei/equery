package com.asiainfo.billing.drquery.infogather;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.ClassUtils;

/**
 * 异常日志处理切面基础类,实现参见{@link ExceptionInterceptor}
 * 
 * @author Rex wong
 * @version 1.1,2011-7-20
 * @since framework1.1
 */
public class ExceptionAspectSupport {
	/**
	 * 得到方法路径
	 * 
	 * @param method
	 * @param targetClass
	 * @return
	 */
	protected String methodIdentification(Method method, Class targetClass) {
		Method specificMethod = ClassUtils.getMostSpecificMethod(method,
				targetClass);
		return ClassUtils.getQualifiedMethodName(specificMethod);
	}

	/**
	 * 构建业务异常信息
	 * 
	 * @param targetClass
	 * @param methodName
	 * @param args
	 * @param exceptionInfo
	 * @return
	 */
	protected String buildExceptionInfo(Class<?> targetClass,
			String methodName, Object[] args, String exceptionInfo) {
		StringBuilder sb = new StringBuilder();
		sb.append("[ClassName:");
		sb.append(ClassUtils.getShortName(targetClass));
		sb.append("]");
		if (StringUtils.isNotEmpty(methodName)) {
			sb.append("[method:");
			sb.append(methodName);
			sb.append("]");
		}

		if (args != null && args.length != 0) {
			sb.append("[");
			for (int i = 0; i < args.length; i++) {
				if (i == 0) {
					sb.append("arg").append(i).append(":").append(args[i]);
				} 
				else {
					sb.append(",").append("arg").append(i).append(":")
							.append(args[i]);
				}

			}
			sb.append("]");
		}
		if (exceptionInfo != null) {
			sb.append("\r\n" + exceptionInfo);
		}
		return sb.toString();
	}

	/**
	 * @param request
	 * @return
	 */
	protected String buildControlExceptionInfo(HttpServletRequest request) {
		String protocol = request.getProtocol();
		String url = protocol.substring(0, protocol.indexOf("/")) + "://"
				+ request.getRemoteAddr() + ":" + request.getRemotePort();
		StringBuilder exinfosb = new StringBuilder();
		exinfosb.append("调用URL[");
		exinfosb.append(url);
		exinfosb.append("]-");
		exinfosb.append("REST接口[");
		exinfosb.append(request.getRequestURI());
		exinfosb.append("]-");
		exinfosb.append("调用方式[");
		exinfosb.append(request.getMethod());
		exinfosb.append("]-");
		exinfosb.append("参数[");
		exinfosb.append(request.getQueryString());
		exinfosb.append("]");
		return exinfosb.toString();
	}
}
