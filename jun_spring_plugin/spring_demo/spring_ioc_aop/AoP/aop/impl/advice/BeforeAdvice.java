package aop.impl.advice;

import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;

public class BeforeAdvice implements MethodBeforeAdvice{

	@Override
	public void before(Method method, Object[] args, Object obj) throws Throwable {
		String name=(String) args[0];
		System.out.println("Welcome in.. "+name);	
	}
}
