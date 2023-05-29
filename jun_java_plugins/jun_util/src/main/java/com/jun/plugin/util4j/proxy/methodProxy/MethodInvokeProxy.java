package com.jun.plugin.util4j.proxy.methodProxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 方法调用句柄
 * @author juebanlin
 */
public class MethodInvokeProxy {
	private final Object target;
	private final Method method;
	private final Object[] args;

	public MethodInvokeProxy(Object target, Method method, Object... args) {
		super();
		this.target = target;
		this.method = method;
		this.args = args;
	}

	public void invoke() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		method.setAccessible(true);
		if (args == null || args.length==0) {
			method.invoke(target);
		} else {
			method.invoke(target, args);
		}
	}

	public Object getTarget() {
		return target;
	}

	public Method getMethod() {
		return method;
	}

	public Object[] getArgs() {
		return args;
	}

	@Override
	public String toString() {
		return "MethodInvokeHandle [target=" + target + ", method=" + method + ", args=" + Arrays.toString(args) + "]";
	}
	public static void main(String[] args) {
		new MethodInvokeProxy(new Object(), null);
	}
}