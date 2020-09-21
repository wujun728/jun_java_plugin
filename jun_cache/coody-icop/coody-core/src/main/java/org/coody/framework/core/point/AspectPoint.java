package org.coody.framework.core.point;

import java.lang.reflect.Method;

import org.coody.framework.core.entity.BaseModel;

import net.sf.cglib.proxy.MethodProxy;

@SuppressWarnings("serial")
public class AspectPoint extends BaseModel{
	
	//业务bean
	private Object bean;
	//业务方法
	private Method method;
	//代理
	private MethodProxy proxy;
	//业务所在class
	private Class<?> clazz;
	//业务方法所需参数
	private Object[] params;
	private AspectPoint childPoint;
	//切面方法
	private Method aspectMethod;
	//切面bean
	private Object aspectBean;
	
	
	public Object getBean() {
		return bean;
	}
	public void setBean(Object bean) {
		this.bean = bean;
	}
	public Method getMethod() {
		return method;
	}
	public void setMethod(Method method) {
		this.method = method;
	}
	public MethodProxy getProxy() {
		return proxy;
	}
	public void setProxy(MethodProxy proxy) {
		this.proxy = proxy;
	}
	public Class<?> getClazz() {
		return clazz;
	}
	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}
	public Object[] getParams() {
		return params;
	}
	public void setParams(Object[] params) {
		this.params = params;
	}
	public Method getAspectMethod() {
		return aspectMethod;
	}
	public void setAspectMethod(Method aspectMethod) {
		this.aspectMethod = aspectMethod;
	}
	public AspectPoint getChildPoint() {
		return childPoint;
	}
	public void setChildPoint(AspectPoint childPoint) {
		this.childPoint = childPoint;
	}
	public Object getAspectBean() {
		return aspectBean;
	}
	public void setAspectBean(Object aspectBean) {
		this.aspectBean = aspectBean;
	}
	
	public Object invoke() throws Throwable{
		if(childPoint==null){
			return proxy.invokeSuper(bean, params);
		}
		childPoint.setParams(params);
		return childPoint.getAspectMethod().invoke(childPoint.getAspectBean(), childPoint);
	}
	
	
}
