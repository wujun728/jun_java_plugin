package aop2.vip;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class RoundAdvice implements MethodInterceptor{
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object[] args=invocation.getArguments();
		System.out.println("Before-args[0]:"+args[0]);
		Object result=  invocation.proceed();
        System.out.println("After-result:"+result);
		return result;
	}
}
