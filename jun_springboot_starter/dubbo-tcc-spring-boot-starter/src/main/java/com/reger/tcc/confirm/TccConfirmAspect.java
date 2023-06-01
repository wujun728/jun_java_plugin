package com.reger.tcc.confirm;

import java.lang.reflect.Method;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.jboss.netty.util.internal.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionOperations;
import org.springframework.util.StringUtils;

import com.reger.tcc.annotation.Commit;
import com.reger.tcc.core.TccTransaction;
import com.reger.tcc.core.TransactionTemplateUtils;
import com.reger.tcc.exception.TccRuntimeException;

@Aspect
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TccConfirmAspect {

	private static final Logger log = LoggerFactory.getLogger(TccConfirmAspect.class);

	private static final TransactionOperations getTransactionTemplate(TccConfirm confirm) {
		return TransactionTemplateUtils.getTransactionTemplate(confirm.getConfirm());
	}

	private final static Map<Method, TccConfirm> tccConfirms = new ConcurrentHashMap<Method, TccConfirm>();

	@Around(value = "@annotation(com.reger.tcc.annotation.Confirm)", argNames = "point")
	public Object doAround(final ProceedingJoinPoint point) throws Throwable {
		TccConfirm confirm = this.getConfirm(point);
		if (confirm == null) {
			log.info("没有定义transactionTemplate，没法被事务管理");
			return point.proceed();
		}
		return this.transaction(point, confirm);
	}

	private TccConfirm getConfirm(ProceedingJoinPoint point) throws SecurityException, NoSuchMethodException {
		MethodSignature ms = (MethodSignature) point.getSignature();
		Method method = ms.getMethod();

		Class<?> targetClass = point.getTarget().getClass();
		Method targetMethod = targetClass.getMethod(method.getName(), method.getParameterTypes());
		if (tccConfirms.containsKey(targetMethod)) {
			return tccConfirms.get(targetMethod);
		}
		Commit confirm = AnnotationUtils.findAnnotation(targetMethod, Commit.class);
		TccConfirm tccConfirm;
		if (confirm == null) {
			tccConfirm = new TccConfirm(null, null);
		} else {
			tccConfirm = new TccConfirm(confirm, getRollBackName(confirm));
		}
		tccConfirms.put(targetMethod, tccConfirm);
		return tccConfirm;
	}

	private String getRollBackName(Commit confirm) {
		String rollBack = confirm.rollBack();
		if (StringUtils.isEmpty(rollBack)) {
			rollBack = confirm.value();
		}
		return rollBack;

	}

	private Object transaction(final ProceedingJoinPoint point, final TccConfirm confirm) throws Throwable {
		TransactionOperations transactionTemplate = getTransactionTemplate(confirm);
		int retry = confirm.getRetry();
		while (true) {
			try {
				return transactionTemplate.execute(new TransactionCallback<Object>() {
					@Override
					public Object doInTransaction(TransactionStatus status) {
						try {
							TccTransaction transaction=	TccTransaction.create( confirm.getName(), confirm.getRollBackName());
							transaction.start();
							Object relust = point.proceed();
							transaction.commit();
							return relust;
						} catch (Throwable e) {
							boolean isRollBack = confirm.isRollBack(e);
							throw new TccRuntimeException(e,isRollBack);
						}
					}
				});
			} catch (TccRuntimeException e) {
				if(!e.isRollback()){ // 事务不需要回滚，直接抛出异常，不给重试
					throw e.getCause();
				}
				if (retry-- <= 0) {// 重试次数超过限制，抛出异常，同时发起分布式事务的回滚消息
					throw e.getCause();
				}
			}
		}
	}

}
