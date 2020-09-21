package com.opensource.nredis.proxy.monitor.enums;

public enum TESTENVEnums {
	
	开发(1,"开发"),集成(2,"集成"),PUT(3,"生产");
	private Integer code;
	
	private String message;
	
		
	
	/**
	 * @param code
	 * @param message
	 */
	private TESTENVEnums(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	/**
	 * @return the code
	 */
	public Integer getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(Integer code) {
		this.code = code;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	public static String getMessage(int code) {
		for (TESTENVEnums c : TESTENVEnums.values()) {
			if (c.getCode()== code) {
				return c.message;
			}
		}
		return null;
	}
}
