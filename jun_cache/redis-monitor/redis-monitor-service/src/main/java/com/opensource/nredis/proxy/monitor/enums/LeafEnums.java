/**
 * 
 */
package com.opensource.nredis.proxy.monitor.enums;

/**
 * @author liubing
 *
 */
public enum LeafEnums {
	
	 // 2**
    LEAF("01", "是"), NO_LEAF("02","否");
	
	private String code;
	
	private String message;

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
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

	/**
	 * @param code
	 * @param message
	 */
	private LeafEnums(String code, String message) {
		this.code = code;
		this.message = message;
	}
	
	
}
