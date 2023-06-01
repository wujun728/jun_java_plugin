package com.reger.tcc.rollback;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.transaction.support.TransactionOperations;

import com.reger.tcc.annotation.RollBack;
import com.reger.tcc.core.TransactionTemplateUtils;

public class TccRollBack {
	private final Object bean;
	private final String beanName;
	private final String rollBackName;
	private final Method method;
	private final RollBack rollBack;
	private TransactionOperations transactionTemplate;
	private final Set<Class<? extends Throwable>> rollBackThrowable = new HashSet<Class<? extends Throwable>>();

	public TccRollBack(Object bean, String beanName, Method method, RollBack rollBack) {
		super();
		this.bean = bean;
		this.beanName = beanName;
		this.method = method;
		this.rollBack = rollBack;
		this.rollBackName = rollBack.value();
		rollBackThrowable.addAll(Arrays.asList(rollBack.noRollbackFor()));
	}

	/**
	 * 判断异常是否需要回滚
	 */
	public boolean isRollBack(Throwable throwable) {
		if (throwable == null) {
			return false;
		}
		if (rollBackThrowable.contains(throwable.getClass())) {
			return false;
		}
		return true;
	}

	public synchronized TransactionOperations getTransactionTemplate() {
		if (transactionTemplate == null) {
			transactionTemplate = TransactionTemplateUtils.getTransactionTemplate(rollBack);
		}
		return transactionTemplate;
	}

	public String getRollBackName() {
		return rollBackName;
	}

	public Object getBean() {
		return bean;
	}

	public String getBeanName() {
		return beanName;
	}

	public Method getMethod() {
		return method;
	}

	public RollBack getRollBack() {
		return rollBack;
	}

	@Override
	public String toString() {
		return "TccRollBack [beanName=" + beanName + ", method=" + method + ", cancel=" + rollBack.value() + "]";
	}

	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}
}
