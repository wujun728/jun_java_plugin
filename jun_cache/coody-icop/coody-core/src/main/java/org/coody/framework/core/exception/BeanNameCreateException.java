package org.coody.framework.core.exception;

import org.coody.framework.core.exception.base.IcopException;

@SuppressWarnings("serial")
public class BeanNameCreateException extends IcopException{


	public BeanNameCreateException(Class<?> clazz){
		super("BeanName创建失败 >>"+clazz.getName());
	}
}
