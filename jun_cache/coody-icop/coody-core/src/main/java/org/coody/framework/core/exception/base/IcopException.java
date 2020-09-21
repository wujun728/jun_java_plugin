package org.coody.framework.core.exception.base;

@SuppressWarnings("serial")
public class IcopException extends RuntimeException{

	public IcopException(){
		super();
	}
	
	public IcopException(String msg){
		super(msg);
	}
	
	public IcopException(String msg,Exception e){
		super(msg, e);
	}
}
