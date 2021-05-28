package org.smartboot.service.api;

/**
 * 调用api的返回码
 * 
 * @author Wujun
 * @version ApiCodeEnum.java, v 0.1 2016年2月10日 下午3:03:30 Seer Exp. 
 */
public enum ApiCodeEnum {
	/** 成功 */
	SUCCESS(0, "成功"),
	/** 失败 */
	FAIL(1, "失败"),
	/** 业务异常 */
	BIZERROR(2, "业务异常");
	private ApiCodeEnum(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	private int code;
	private String desc;

	/**
	 * Getter method for property <tt>code</tt>.
	 *
	 * @return property value of code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * Setter method for property <tt>code</tt>.
	 *
	 * @param code
	 *            value to be assigned to property code
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * Getter method for property <tt>desc</tt>.
	 *
	 * @return property value of desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * Setter method for property <tt>desc</tt>.
	 *
	 * @param desc
	 *            value to be assigned to property desc
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

}
