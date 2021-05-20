package io.gitee.tooleek.lock.spring.boot.core.strategy;

import io.gitee.tooleek.lock.spring.boot.constant.LockCommonConstant;
import io.gitee.tooleek.lock.spring.boot.core.LockKey.Builder;
import io.gitee.tooleek.lock.spring.boot.exception.KeyBuilderException;

import java.lang.reflect.Method;

/**
 * @BelongsPackage: io.gitee.tooleek.lock.spring.boot.core.strategy
 * @Author: zsx
 * @CreateTime: 2019-04-17 10:01
 * @Description:
 */
public abstract class KeyStrategy {

	protected String className;
	protected String methodName;
	protected Method realMethod;
	protected Object[] args;

	public KeyStrategy(String className, String methodName, Method realMethod, Object[] args) {
		this.className = className;
		this.methodName = methodName;
		this.realMethod = realMethod;
		this.args = args;
	}

	public String getSimpleClassName() {
		return this.className.split("\\.")[this.className.split("\\.").length - 1];
	}

	/**
	 * 包装锁的key标记
	 * 
	 * @param valTag
	 * @return
	 */
	public String wrapKeyTag(String valTag) {
		return getSimpleClassName() + LockCommonConstant.KEY_SPLIT_MARK + this.methodName
				+ LockCommonConstant.KEY_SPLIT_MARK + valTag;
	}

	/**
	 * 添加锁
	 * 
	 * @param keyBuilder
	 * @param realMethod
	 * @param args
	 */
	public abstract Builder generateBuilder() throws KeyBuilderException;

}
