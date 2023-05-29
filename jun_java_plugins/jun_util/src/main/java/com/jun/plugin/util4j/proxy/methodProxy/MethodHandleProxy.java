package com.jun.plugin.util4j.proxy.methodProxy;

import java.lang.invoke.MethodHandle;
import java.util.Arrays;

/**
 * 方法调用句柄
 * @author juebanlin
 */
public class MethodHandleProxy {
	private final Object target;
	private final MethodHandle methodHandle;
	private final Object[] args;

	public MethodHandleProxy(Object target, MethodHandle methodHandle, Object... args) {
		super();
		this.target = target;
		this.methodHandle = methodHandle;
		this.args = args;
	}

	public void invoke() throws Throwable{
		methodHandle.invokeExact(target,args);
	}

	public Object getTarget() {
		return target;
	}

	public MethodHandle getMethod() {
		return methodHandle;
	}

	public Object[] getArgs() {
		return args;
	}

	@Override
	public String toString() {
		return "MethodHandleProxy [target=" + target + ", methodHandle=" + methodHandle + ", args="
				+ Arrays.toString(args) + "]";
	}
}