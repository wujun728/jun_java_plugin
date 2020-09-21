/**
 * 
 */
package com.opensource.nredis.proxy.monitor.enums;

/**
 * @author liubing
 *
 */
public enum SpreadEnums {
	
	TRUE(1, true), FALSE(2,false);
	
	private int code;
	
	private Boolean flag;

	/**
	 * @param code
	 * @param flag
	 */
	private SpreadEnums(int code, Boolean flag) {
		this.code = code;
		this.flag = flag;
	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * @return the flag
	 */
	public Boolean getFlag() {
		return flag;
	}

	/**
	 * @param flag the flag to set
	 */
	public void setFlag(Boolean flag) {
		this.flag = flag;
	}
	
	
}
