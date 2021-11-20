package aop.impl.advice;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class RoundAdvice implements MethodInterceptor{

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object[] args=invocation.getArguments();
		String name=(String) args[0];
		System.out.println("Before..Mr."+name);
		  Object obj=invocation.proceed();
		System.out.println("After..Mr."+name);
		return obj;
	}

}
