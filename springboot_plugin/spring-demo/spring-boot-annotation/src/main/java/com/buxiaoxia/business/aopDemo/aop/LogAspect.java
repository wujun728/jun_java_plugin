package com.buxiaoxia.business.aopDemo.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Created by xw on 2017/3/24.
 * 2017-03-24 23:17
 */
@Slf4j
@Aspect
@Component
public class LogAspect {


	// 指定切点为 注解@LogAnnotation
	@Pointcut("@annotation(com.buxiaoxia.business.aopDemo.annotation.LogAnnotation)")
	public void logMessage() {
	}


	/**
	 * 前置增强
	 *
	 * @param joinPoint
	 */
	@Before("logMessage()")
	public void doBefore(JoinPoint joinPoint) {
		log.info("注解结合AOP实现的前置增强");
		log.info("JoinPoint类提供了方法的各种信息，包括目标对象，签名，代理方法，参数等，相关信息可以查看对应文档获取");
		log.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." +
				joinPoint.getSignature().getName());
		log.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
	}


	/**
	 * 后置返回增强（目标方法只要执行完了返回后执行后置增强）
	 *
	 * @param joinPoint
	 * @param ret
	 */
	@AfterReturning(value = "logMessage()", returning = "ret")
	public void doAfterReturn(JoinPoint joinPoint, Object ret) {
		log.info("注解结合AOP实现的后置返回增强");
		log.info("=====================================");
		log.info("此处可以实现方法返回的日志打印，甚至消息发送等相关处理，可以根据业务情况自定义");
		log.info("=====================================");

	}

	/**
	 * 后置最终增强（目标方法只要执行完了就会执行后置增强）
	 *
	 * @param joinPoint
	 */
	@After("logMessage()")
	public void doAfter(JoinPoint joinPoint) {
		log.info("注解结合AOP实现的后置增强");
	}
}
