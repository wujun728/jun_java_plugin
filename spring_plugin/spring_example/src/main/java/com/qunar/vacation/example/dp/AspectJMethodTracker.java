package com.qunar.vacation.example.dp;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class AspectJMethodTracker {
	@Pointcut("@annotation(com.qunar.vacation.example.dp.Track)")
	public void methodTrackerPointcut(){
	}
	
	@Around("methodTrackerPointcut()")
	public void track(ProceedingJoinPoint pjp){
		String methodName = pjp.getSignature().getName();
		System.out.println("Before call " + methodName);
		try {
			pjp.proceed();
		} catch (Throwable e) {
			System.out.println("Catch exception: " + e.getMessage());
		}
		System.out.println("After call " + methodName);
	}
}
