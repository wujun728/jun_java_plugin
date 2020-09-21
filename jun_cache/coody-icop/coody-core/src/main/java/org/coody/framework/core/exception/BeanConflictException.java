package org.coody.framework.core.exception;

import org.coody.framework.core.exception.base.IcopException;

@SuppressWarnings("serial")
public class BeanConflictException extends IcopException{


	public BeanConflictException(String bean){
		super("Bean冲突 >>"+bean);
	}
	
	public BeanConflictException(String bean,Exception e){
		super("Bean冲突 >>"+bean, e);
	}
}
