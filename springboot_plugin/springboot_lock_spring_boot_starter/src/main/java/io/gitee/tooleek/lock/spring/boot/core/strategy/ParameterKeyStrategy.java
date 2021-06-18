package io.gitee.tooleek.lock.spring.boot.core.strategy;

import io.gitee.tooleek.lock.spring.boot.annotation.Key;
import io.gitee.tooleek.lock.spring.boot.core.LockKey;
import io.gitee.tooleek.lock.spring.boot.core.LockKey.Builder;
import io.gitee.tooleek.lock.spring.boot.exception.KeyBuilderException;

import java.lang.reflect.Method;

/**
 * @BelongsPackage: io.gitee.tooleek.lock.spring.boot.core.strategy
 * @Author: zsx
 * @CreateTime: 2019-04-17 10:00
 * @Description: 参数锁处理
 */
public class ParameterKeyStrategy extends KeyStrategy {

	public ParameterKeyStrategy(String className, String methodName, Method realMethod, Object[] args) {
		super(className, methodName, realMethod, args);
	}

	@Override
	public Builder generateBuilder() throws KeyBuilderException {
		Builder keyBuilder = LockKey.newBuilder();
		for (int i = 0; i < realMethod.getParameters().length; i++) {
			if (realMethod.getParameters()[i].isAnnotationPresent(Key.class)) {
				keyBuilder.appendKey(wrapKeyTag(args[i].toString()));
			}
		}
		return keyBuilder;
	}
	
}
