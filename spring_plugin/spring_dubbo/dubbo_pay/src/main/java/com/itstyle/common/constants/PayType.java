package com.itstyle.common.constants;
/**
 * 支付类型
 * 创建者 科帮网
 * 创建时间	2017年8月2日
 *
 */
public enum PayType {
	/**支付类型*/
	ALI("支付宝",(short)1),WECHAT("微信",(short)2),UNION("银联",(short)3);
	
	private Short code;
	private String name;
	
	private PayType(String name, Short code) {
		this.name = name;
		this.code = code;
	}

	public static String getName(Short code,String name) {
		for (PayType c : PayType.values()) {
			if (c.getCode() == code) {
				return c.name;
			}
		}
		return null;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public short getCode() {
		return code;
	}

	public void setCode(short code) {
		this.code = code;
	}
}
