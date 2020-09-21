package org.coody.framework.web.entity;

import java.lang.reflect.Method;
import java.util.List;

import org.coody.framework.core.entity.BaseModel;
import org.coody.framework.core.entity.BeanEntity;
import org.coody.framework.web.adapt.iface.IcopParamsAdapt;

@SuppressWarnings("serial")
public class MvcMapping extends BaseModel {

	private String path;

	private Method method;

	private Object bean;

	private List<BeanEntity> paramTypes;

	private IcopParamsAdapt paramsAdapt;

	public IcopParamsAdapt getParamsAdapt() {
		return paramsAdapt;
	}

	public void setParamsAdapt(IcopParamsAdapt paramsAdapt) {
		this.paramsAdapt = paramsAdapt;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public Object getBean() {
		return bean;
	}

	public void setBean(Object bean) {
		this.bean = bean;
	}

	public List<BeanEntity> getParamTypes() {
		return paramTypes;
	}

	public void setParamTypes(List<BeanEntity> paramTypes) {
		this.paramTypes = paramTypes;
	}

}