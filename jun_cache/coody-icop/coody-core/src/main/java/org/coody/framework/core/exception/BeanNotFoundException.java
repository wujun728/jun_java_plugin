package org.coody.framework.core.exception;

import org.coody.framework.core.exception.base.IcopException;

@SuppressWarnings("serial")
public class BeanNotFoundException extends IcopException{

	
	public BeanNotFoundException(String bean){
		super("未找到Bean >>"+bean);
	}
	
	public BeanNotFoundException(String bean,Class<?> clazz){
		super("未找到Bean >>"+bean+" by "+clazz.getName());
	}
	
	public BeanNotFoundException(String bean,Exception e){
		super("未找到Bean >>"+bean, e);
	}
}
