package org.coody.framework.core.exception;

import org.coody.framework.core.exception.base.IcopException;

@SuppressWarnings("serial")
public class BeanInitException extends IcopException{


	public BeanInitException(Class<?> clazz){
		super("Bean初始化失败 >>"+clazz.getName());
	}
}
