package com.asiainfo.billing.drquery.infogather;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 异常AOP拦截
 * 
 * @author Rex wong
 * @version 1.0,2011-7-20
 * @since
 */
@Component("exceptionInterceptor")
@Aspect
public final class ExceptionInterceptor extends ExceptionAspectSupport implements HandlerInterceptor {
	private static final Log _log = LogFactory.getLog(ExceptionInterceptor.class);// 默认系统日志
	private Throwable _ex = null;

	@Around(value = "execution(public * *(..))")
	public Object invoke(ProceedingJoinPoint pip) throws Exception{
		Class<?> targetClass = pip.getTarget().getClass();
		String methodName = pip.getSignature().getName();
		Object retval = null;
		try {
			retval = pip.proceed();
		} 
		catch (Throwable e) {
			String _errorMsg = e.getMessage();
			String errorInfo = buildExceptionInfo(targetClass, methodName,
					pip.getArgs(), _errorMsg);
			_log.error(errorInfo,e);
			this._ex = e;
		}
		return retval;
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
								HttpServletResponse response, 
								Object handler, 
								ModelAndView modelAndView) throws Exception {
		
		Map<String, Object> model = modelAndView.getModel();
		Object modelresponse = model.get("response");
		if (this._ex != null) {
			String exceptionInfo = buildControlExceptionInfo(request);
			_log.error(
					buildExceptionInfo(handler.getClass(), "", null,
							exceptionInfo), this._ex.getCause());
			try {
				throw this._ex;
			} 
			catch (Throwable e) {
				e.printStackTrace();
			} 
			finally {
				this._ex = null;
			}
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		if (ex == null) {
			return;
		}
		String exinfoStr = buildControlExceptionInfo(request);
		_log.error(buildExceptionInfo(handler.getClass(), "", null, exinfoStr),ex.getCause());
		throw new Exception(ex.getMessage());
	}
}
