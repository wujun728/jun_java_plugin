package cn.com.bluemoon.common.logs;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 统一日志处理
 * @author Wujun
 * @date 2019年1月3日 上午11:29:13
 *
 */
@Aspect
@Component
@Order(1)
public class LogAspect {
	
	private Logger logger = LoggerFactory.getLogger(LogAspect.class);
	
	ThreadLocal<Long> startTime = new ThreadLocal<Long>();
	
	@Pointcut("execution(public * cn.com.bluemoon.controller.*.*(..))")
	public void logPointCut() {}
	
	/**
	 * 在切点前执行
	 * @param joinPoint
	 */
	@Before("logPointCut()")
	public void doBefore(JoinPoint joinPoint) {
		startTime.set(System.currentTimeMillis());
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
	    HttpServletRequest request = requestAttributes.getRequest();
	    String url = request.getRequestURL().toString();
	    String httpMethod = request.getMethod();
	    String ip = getIpAddr(request);
	    String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "."+ joinPoint.getSignature().getName();
	    String parameters = Arrays.toString(joinPoint.getArgs());
	    logger.info("REQUEST URL:" + url + " | HTTP METHOD: " + httpMethod + " | IP: " + ip + " | CLASS_METHOD: " + classMethod
	    		+ " | ARGS:" + parameters);
	}

	/**
	 * 在切点后，return前执行
	 * @param joinPoint
	 */
	@After("logPointCut()")
	public void doAfter(JoinPoint joinPoint) {}
	
	/**
	 * 在切入点，return后执行，如果相对某些方法的返回参数进行处理，可以在此处执行
	 * @param object
	 */
	@AfterReturning(returning = "object",pointcut = "logPointCut()")
    public void doAfterReturning(Object object){
		logger.info("RESPONSE TIME: "+ (System.currentTimeMillis() - startTime.get()) + "ms");
		logger.info("RESPONSE BODY: "+ object);
    }

	/**
	 * 获取真实的IP地址
	 * @param request
	 * @return
	 */
	private String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
