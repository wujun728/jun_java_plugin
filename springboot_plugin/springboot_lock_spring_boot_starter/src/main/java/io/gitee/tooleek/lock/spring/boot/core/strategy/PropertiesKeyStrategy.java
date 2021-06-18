package io.gitee.tooleek.lock.spring.boot.core.strategy;

import io.gitee.tooleek.lock.spring.boot.annotation.Key;
import io.gitee.tooleek.lock.spring.boot.core.LockKey;
import io.gitee.tooleek.lock.spring.boot.core.LockKey.Builder;
import io.gitee.tooleek.lock.spring.boot.exception.KeyBuilderException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @BelongsPackage: io.gitee.tooleek.lock.spring.boot.core.strategy
 * @Author: zsx
 * @CreateTime: 2019-04-17 10:00
 * @Description: 属性锁处理
 */
public class PropertiesKeyStrategy extends KeyStrategy {

    public PropertiesKeyStrategy(String className, String methodName, Method realMethod, Object[] args) {
		super(className, methodName, realMethod, args);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Builder generateBuilder() throws KeyBuilderException {
		Builder keyBuilder = LockKey.newBuilder();
		for (int i = 0; i < args.length; i++) {
            Object obj = args[i];
            Class objectClass = obj.getClass();
            Field[] fields = objectClass.getDeclaredFields();
            for (Field field : fields) {
                if (null != field.getAnnotation(Key.class)) {
                    field.setAccessible(true);
                    try {
						keyBuilder.appendKey(wrapKeyTag(field.get(obj).toString()));
					} catch (IllegalArgumentException e) {
						throw new KeyBuilderException("生成builder失败",e);
					} catch (IllegalAccessException e) {
						throw new KeyBuilderException("生成builder失败",e);
					}
                }
            }
        }
		return keyBuilder;
	}
	
}
