package aop.impl.advice;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;

public class AfterAdvice implements AfterReturningAdvice{

	@Override
	public void afterReturning(Object obj, 
			                   Method method, 
			                   Object[] args, 
			                   Object obj2) throws Throwable {
		String name=(String) args[0];
		System.out.println("Good bye.. "+name);
		
	}

}
