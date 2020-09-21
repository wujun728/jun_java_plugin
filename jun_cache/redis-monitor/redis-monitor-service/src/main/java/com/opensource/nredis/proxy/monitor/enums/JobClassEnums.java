/**
 * 
 */
package com.opensource.nredis.proxy.monitor.enums;

/**
 * @author liubing
 *
 */
public enum JobClassEnums {
	
	NON_BLOCK(1,"并行","com.opensource.nredis.proxy.monitor.quartz.utils.ConcurrentExecutionJobQuartz"),BLOCK(2,"串行","com.opensource.nredis.proxy.monitor.quartz.utils.ConcurrentExecutionJobQuartz");
	
	private Integer code;
	
	private String message;

	private String className;

	/**
	 * @param code
	 * @param message
	 * @param className
	 */
	private JobClassEnums(Integer code, String message, String className) {
		this.code = code;
		this.message = message;
		this.className = className;
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

	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @param className the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}
	
	public static String getClass(int code) {
		for (JobClassEnums c : JobClassEnums.values()) {
			if (c.getCode()== code) {
				return c.className;
			}
		}
		return null;
	}
	
	public static String getMessage(int code) {
		for (JobClassEnums c : JobClassEnums.values()) {
			if (c.getCode()== code) {
				return c.message;
			}
		}
		return null;
	}
}
