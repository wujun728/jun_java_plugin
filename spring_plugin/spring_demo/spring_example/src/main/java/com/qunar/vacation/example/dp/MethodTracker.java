package com.qunar.vacation.example.dp;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class MethodTracker implements InvocationHandler{
	private Object obj;
	
	public MethodTracker(Object obj) {
		super();
		this.obj = obj;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Track track = method.getAnnotation(Track.class);
		if(track == null) {
			return method.invoke(obj, args);
		}
		
		Object result = null;
		System.out.println("Before call " + method.getName());
		try{
			result = method.invoke(obj, args);
		}catch(InvocationTargetException e){
			System.err.println("Found exception: " + e.getMessage());
			throw e.getCause();
		}

		System.out.println("After call " + method.getName());
		return result;
	}

}
