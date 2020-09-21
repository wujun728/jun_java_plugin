package org.coody.framework.core.proxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.coody.framework.core.constant.FrameworkConstant;
import org.coody.framework.core.container.BeanContainer;
import org.coody.framework.core.container.InterceptContainer;
import org.coody.framework.core.entity.AspectEntity;
import org.coody.framework.core.point.AspectPoint;
import org.coody.framework.core.util.AntUtil;
import org.coody.framework.core.util.MethodSignUtil;
import org.coody.framework.core.util.PropertUtil;
import org.coody.framework.core.util.StringUtil;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CglibProxy implements MethodInterceptor {



	public Object getProxy(Class<?> clazz)  {
		Integer modifier = clazz.getModifiers();
		if (Modifier.isAbstract(modifier)) {
			return null;
		}
		if (Modifier.isInterface(modifier)) {
			return null;
		}
		if (!isNeedProxyMethods(clazz)) {
			try {
				return clazz.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(clazz);
		enhancer.setCallback(this);
		return enhancer.create();
	}

	private boolean isNeedProxyMethods(Class<?> clazz) {
		if (StringUtil.isNullOrEmpty(clazz.getDeclaredMethods())) {
			return false;
		}
		boolean needProxy = false;
		for (Method method : clazz.getDeclaredMethods()) {
			for (List<AspectEntity> aspectEntitys : FrameworkConstant.ASPECT_MAP.values()) {
				for (AspectEntity aspectEntity : aspectEntitys) {
					if (!needProxy(clazz, aspectEntity, method)) {
						continue;
					}
					if (InterceptContainer.INTERCEPT_MAP.containsKey(method)) {
						InterceptContainer.INTERCEPT_MAP.get(method).add(aspectEntity.getAspectInvokeMethod());
						needProxy = true;
						continue;
					}
					Set<Method> aspectMethods = new HashSet<Method>();
					aspectMethods.add(aspectEntity.getAspectInvokeMethod());
					InterceptContainer.INTERCEPT_MAP.put(method, aspectMethods);
				}
				needProxy = true;
			}
		}
		return needProxy;
	}

	private boolean needProxy(Class<?> clazz, AspectEntity aspectEntity, Method method) {
		/**
		 * 判断类名是否满足条件
		 */
		if (!StringUtil.isNullOrEmpty(aspectEntity.getClassMappath())) {
			if (!AntUtil.isAntMatch(clazz.getName(), aspectEntity.getClassMappath())) {
				return false;
			}
		}
		/**
		 * 判断方法名是否满足条件
		 */
		if (!StringUtil.isNullOrEmpty(aspectEntity.getMethodMappath())) {
			if (!AntUtil.isAntMatch(MethodSignUtil.getMethodUnionKey(method), aspectEntity.getMethodMappath())) {
				return false;
			}
		}
		/**
		 * 判断注解是否满足条件
		 */
		if (!StringUtil.isNullOrEmpty(aspectEntity.getAnnotationClass())) {
			Annotation[] annotations = method.getAnnotations();
			if (StringUtil.isNullOrEmpty(annotations)) {
				return false;
			}
			List<Class<?>> annotationClazzs = new ArrayList<Class<?>>();
			for (Annotation annotation : annotations) {
				annotationClazzs.add(annotation.annotationType());
			}
			for (Class<?> aspectAnnotationClazz : aspectEntity.getAnnotationClass()) {
				if (!annotationClazzs.contains(aspectAnnotationClazz)) {
					return false;
				}
			}
		}
		return true;
	}

	// 拦截父类所有方法的调用
	@Override
	public Object intercept(Object bean, Method method, Object[] params, MethodProxy proxy) throws Throwable {
		if (!InterceptContainer.INTERCEPT_MAP.containsKey(method)) {
			//该方法不存在AOP拦截
			return proxy.invokeSuper(bean, params);
		}
		//获取拦截该方法的切面
		AspectPoint point = getMethodPoint(bean, method, proxy);
		if (point == null) {
			//该方法不存在AOP拦截
			return proxy.invokeSuper(bean, params);
		}
		point.setParams(params);
		return point.getAspectMethod().invoke(point.getAspectBean(), point);
	}

	private AspectPoint getMethodPoint(Object bean, Method method, MethodProxy proxy) {
		if (InterceptContainer.METHOD_INTERCEPT_MAP.containsKey(method)) {
			return InterceptContainer.METHOD_INTERCEPT_MAP.get(method);
		}
		List<Method> invokeMethods = new ArrayList<Method>(InterceptContainer.INTERCEPT_MAP.get(method));
		Method aspectMethod = invokeMethods.get(0);
		invokeMethods.remove(0);
		Class<?> clazz = PropertUtil.getClass(aspectMethod);
		Object aspectBean = BeanContainer.getBean(clazz);
		AspectPoint point = new AspectPoint();
		point.setAspectBean(aspectBean);
		point.setAspectMethod(aspectMethod);
		point.setBean(bean);
		point.setClazz(bean.getClass());
		point.setMethod(method);
		point.setProxy(proxy);
		AspectPoint childPoint = parseAspect(point, invokeMethods);
		if (childPoint != null) {
			point.setChildPoint(childPoint);
		}
		InterceptContainer.METHOD_INTERCEPT_MAP.put(method, point);
		return point;
	}

	private AspectPoint parseAspect(AspectPoint basePoint, List<Method> invokeMethods) {
		if (StringUtil.isNullOrEmpty(invokeMethods)) {
			return null;
		}
		Method aspectMethod = invokeMethods.get(0);
		invokeMethods.remove(0);
		Class<?> clazz = PropertUtil.getClass(aspectMethod);
		Object aspectBean = BeanContainer.getBean(clazz);

		AspectPoint point = new AspectPoint();
		point.setAspectBean(aspectBean);
		point.setAspectMethod(aspectMethod);
		point.setBean(basePoint.getBean());
		point.setClazz(basePoint.getBean().getClass());
		point.setMethod(basePoint.getMethod());
		point.setProxy(basePoint.getProxy());
		AspectPoint childPoint = parseAspect(basePoint, invokeMethods);
		if (childPoint != null) {
			point.setChildPoint(childPoint);
			return point;
		}
		return point;
	}
}
