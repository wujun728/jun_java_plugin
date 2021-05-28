package io.springside.springtime.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;

/**
 * 保存通过cglib快速执行服务方法的MethodInvoker
 */
public class ServiceBeanRegistry {
	//key: path
	public Map<String, MethodInvoker> methodInvokerMap = new HashMap<String, MethodInvoker>();

	public void add(String prefix, String serviceName, Object serviceBean) {
		Class<?> serviceClass = serviceBean.getClass();
		FastClass serviceFastClass = FastClass.create(serviceClass);

		Method[] methods = serviceClass.getDeclaredMethods();

		for (Method method : methods) {
			method.setAccessible(true);
			FastMethod fastMethod = serviceFastClass.getMethod(method);
			MethodInvoker methodInvoker = new MethodInvoker(serviceBean, fastMethod);

			methodInvokerMap.put(prefix + "/" + serviceName + "/" + method.getName().toLowerCase(), methodInvoker);
		}
	}

	public MethodInvoker get(String path) {
		return methodInvokerMap.get(path);
	}

	public static class MethodInvoker {
		public Object implBean;
		public FastMethod method;
		public Class requestParameterType;

		public MethodInvoker(Object implBean, FastMethod method) {
			requestParameterType = method.getParameterTypes()[0];
			this.method = method;
			this.implBean = implBean;
		}

		public Object invoke(Object requestParameter) {
			try {
				return method.invoke(implBean, new Object[] { requestParameter });
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
	}
}
