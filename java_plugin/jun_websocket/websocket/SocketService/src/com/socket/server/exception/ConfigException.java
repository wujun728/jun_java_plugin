package com.socket.server.exception;

public class ConfigException extends InterruptedException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8077654329898137881L;

	public ConfigException(String name){
		super(name);
	}

}
