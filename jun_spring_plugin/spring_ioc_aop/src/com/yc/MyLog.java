package com.yc;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;


@Aspect
public class MyLog {
	@Pointcut("execution(* com.yc.*.add*(..)) || execution(* com.yc.*.del*(..))")
	public void xx(){
		
	}
	//@Pointcut
	
	
	@Before("xx()")
	public void log(){
		System.out.println("ǰ֪ͨ");
	}
	

}
