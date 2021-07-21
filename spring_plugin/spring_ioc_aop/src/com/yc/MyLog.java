package com.yc;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;


@Aspect
public class MyLog {
	@Pointcut("execution(* com.yc.*.add*(..)) || execution(* com.yc.*.del*(..))")
	public void xx(){
		
	}
	//@Pointcut切入点
	
	
	@Before("xx()")
	public void log(){
		System.out.println("前置通知");
	}
	

}
