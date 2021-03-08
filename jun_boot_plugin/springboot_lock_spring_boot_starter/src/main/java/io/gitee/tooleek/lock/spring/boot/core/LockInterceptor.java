package io.gitee.tooleek.lock.spring.boot.core;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import io.gitee.tooleek.lock.spring.boot.annotation.Key;
import io.gitee.tooleek.lock.spring.boot.annotation.Lock;
import io.gitee.tooleek.lock.spring.boot.core.LockKey.Builder;
import io.gitee.tooleek.lock.spring.boot.core.strategy.ClassKeyStrategy;
import io.gitee.tooleek.lock.spring.boot.core.strategy.KeyStrategy;
import io.gitee.tooleek.lock.spring.boot.core.strategy.MethodKeyStrategy;
import io.gitee.tooleek.lock.spring.boot.core.strategy.ParameterKeyStrategy;
import io.gitee.tooleek.lock.spring.boot.core.strategy.PropertiesKeyStrategy;
import io.gitee.tooleek.lock.spring.boot.factory.ServiceBeanFactory;
import io.gitee.tooleek.lock.spring.boot.service.LockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 锁拦截器
 *
 * @author Wujun
 */
@Aspect
@Component
public class LockInterceptor {

	Logger logger = LoggerFactory.getLogger(LockInterceptor.class);

	@Autowired
	private ServiceBeanFactory serviceBeanFactory;

	// private ThreadLocal<LockService> localLockService = new ThreadLocal<>();
	private ThreadLocal<Map<String, LockService>> localLockServiceMap = new ThreadLocal<>();

	/**
	 * 获取LockService的Key,用于存放在localLockServiceMap中
	 * @param className	类名
	 * @param methodName	方法名
	 * @return
	 */
	private String getLockServiceKey(String className, String methodName) {
		String simpleClassName = className.split("\\.")[className.split("\\.").length - 1];
		return simpleClassName + "." + methodName;
	}

	@Around(value = "@annotation(io.gitee.tooleek.lock.spring.boot.annotation.Lock)")
	public Object lockHandle(ProceedingJoinPoint joinPoint) throws Throwable {

		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Method targetMethod = methodSignature.getMethod();
		Method realMethod = joinPoint.getTarget().getClass().getDeclaredMethod(methodSignature.getName(),
				targetMethod.getParameterTypes());

		Lock lock = realMethod.getAnnotation(Lock.class);

//        Builder keyBuilder = LockKey.newBuilder()
//                .leaseTime(lock.leaseTime())
//                .waitTime(lock.waitTime())
//                .timeUnit(lock.timeUnit());

		Object[] args = joinPoint.getArgs();

		String className = joinPoint.getTarget().getClass().getName();
		String methodName = methodSignature.getName();

		KeyStrategy keyStrategy = getKeyStrategy(className, methodName, realMethod, args);

		Builder keyBuilder = new KeyStrategyContext(keyStrategy).generateBuilder();

		LockKey lockKey = keyBuilder.leaseTime(lock.leaseTime()).waitTime(lock.waitTime()).timeUnit(lock.timeUnit())
				.build();

		LockService lockService = serviceBeanFactory.getService(lock.lockType());
		lockService.setLockKey(lockKey);

		// localLockService.set(lockService);
		String lockServiceKey = getLockServiceKey(className, methodName);
		Map<String, LockService> lockServiceMap = localLockServiceMap.get();
		if (lockServiceMap == null) {
			lockServiceMap = new HashMap<>();
			localLockServiceMap.set(lockServiceMap);
		}
		lockServiceMap.put(lockServiceKey, lockService);
		
		logger.debug("对[{}]关联的锁进行加锁",lockServiceKey);
		lockService.lock();

		return joinPoint.proceed();
	}

	private KeyStrategy getKeyStrategy(String className, String methodName, Method realMethod, Object[] args) {
		// 参数锁
		for (int i = 0; i < realMethod.getParameters().length; i++) {
			if (realMethod.getParameters()[i].isAnnotationPresent(Key.class)) {
				return new ParameterKeyStrategy(className, methodName, realMethod, args);
			}
		}
		// 方法锁
		if (null != realMethod.getAnnotation(Key.class)) {
			return new MethodKeyStrategy(className, methodName, realMethod, args);
		}
		// 属性锁
		for (int i = 0; i < args.length; i++) {
			Object obj = args[i];
			@SuppressWarnings("rawtypes")
			Class objectClass = obj.getClass();
			Field[] fields = objectClass.getDeclaredFields();
			for (Field field : fields) {
				if (null != field.getAnnotation(Key.class)) {
					return new PropertiesKeyStrategy(className, methodName, realMethod, args);
				}
			}
		}
		// 类名和方法名作为key的锁
		return new ClassKeyStrategy(className, methodName, realMethod, args);
	}

	@AfterReturning(value = "@annotation(io.gitee.tooleek.lock.spring.boot.annotation.Lock)")
	public void afterReturning(JoinPoint joinPoint) {
		release(joinPoint);
	}

	@AfterThrowing(value = "@annotation(io.gitee.tooleek.lock.spring.boot.annotation.Lock)")
	public void afterThrowing(JoinPoint joinPoint) {
		release(joinPoint);
	}

	/**
	 * 释放 锁和LockService
	 * 
	 * @param joinPoint
	 */
	private void release(JoinPoint joinPoint) {
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = ((MethodSignature) joinPoint.getSignature()).getName();

		String lockServiceKey = getLockServiceKey(className, methodName);
		Map<String, LockService> lockServiceMap = localLockServiceMap.get();
		
		logger.debug("[{}]关联的分布式锁解锁",lockServiceKey);
		lockServiceMap.get(lockServiceKey).release();
		
		logger.debug("释放[{}]关联的LockService",lockServiceKey);
		lockServiceMap.remove(lockServiceKey);
		
		if (lockServiceMap.isEmpty()) {
			logger.debug("释放线程[{}]关联的lockServiceMap",Thread.currentThread().getName());
			localLockServiceMap.remove();
		}
		// 原始方法
		// localLockService.get().release();
		// localLockService.remove();
	}

}
