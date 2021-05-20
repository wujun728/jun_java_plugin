package org.smartboot.service.facade.enumeration;

/**
 * 返回码枚举
 * 
 * @author Wujun
 * @version ResultCodeEnum.java, v 0.1 2016年3月6日 下午2:40:37 Seer Exp.
 */
public enum ResultCodeEnum {

	/** 成功 */
	SUCCESS(0, "成功"),

	/** 失败 */
	FAIL(1, "失败");

	private ResultCodeEnum(int code, String desc) {
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
	public final int getCode() {
		return code;
	}

	/**
	 * Setter method for property <tt>code</tt>.
	 *
	 * @param code
	 *            value to be assigned to property code
	 */
	public final void setCode(int code) {
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

	public static ResultCodeEnum getEnumByCode(int code) {
		for (ResultCodeEnum statu : values()) {
			if (code == statu.code) {
				return statu;
			}
		}
		return null;
	}

}
