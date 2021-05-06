package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


public class DynamicProxy implements InvocationHandler {

	private Subject subject;
	
	public Subject getProxyInterface(RealSubject realSubject) {
		this.subject = realSubject;
		
		Subject subject = (Subject)Proxy.newProxyInstance(
				realSubject.getClass().getClassLoader(), 
				realSubject.getClass().getInterfaces(), 
				this);
		
		return subject;
	}
		
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		if (method.getName().startsWith("request")) {
			System.out.println("动态代理成功！");
			subject.request();
		}
		return null;
	}

}
