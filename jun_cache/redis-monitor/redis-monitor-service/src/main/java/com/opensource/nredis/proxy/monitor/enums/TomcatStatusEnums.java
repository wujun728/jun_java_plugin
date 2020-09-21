/**
 * 
 */
package com.opensource.nredis.proxy.monitor.enums;

/**
 * @author liubing
 *
 */
public enum TomcatStatusEnums {
	
	RUNNING(1,"运行"),STOP(2,"停止");
	private Integer code;
	
	private String message;

	/**
	 * @param code
	 * @param message
	 */
	private TomcatStatusEnums(Integer code, String message) {
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
		for (TomcatStatusEnums c : TomcatStatusEnums.values()) {
			if (c.getCode()== code) {
				return c.message;
			}
		}
		return null;
	}
}
