package com.feng.cache.common;

import java.lang.reflect.Method;

import org.springframework.cache.interceptor.KeyGenerator;

public class MyKeyGenerator implements KeyGenerator {

	@Override
	public Object generate(Object target, Method method, Object... params) {
		if (params.length == 0) {
			return target.getClass().getName() + "." +method.getName();
		}
		if (params.length == 1) {
			Object param = params[0];
			if (param != null && !param.getClass().isArray()) {
				return target.getClass().getName() + "." + method.getName() + param;
			}
		}
		return target.getClass().getName() + "." + method.getName();
	}

}
