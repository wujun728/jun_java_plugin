package org.smartboot.service.facade.result;

/**
 * 鉴权对象
 * 
 * @author Wujun
 * @version ToAuth.java, v 0.1 2016年1月20日 下午4:02:00 Seer Exp.
 */
public class ToAuth extends ToString {
	/** 应用码 */
	private String appCode;

	/** 接入码 */
	private String accessKey;

	/**
	 * Getter method for property <tt>appCode</tt>.
	 *
	 * @return property value of appCode
	 */
	public final String getAppCode() {
		return appCode;
	}

	/**
	 * Setter method for property <tt>appCode</tt>.
	 *
	 * @param appCode
	 *            value to be assigned to property appCode
	 */
	public final void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	/**
	 * Getter method for property <tt>accessKey</tt>.
	 *
	 * @return property value of accessKey
	 */
	public final String getAccessKey() {
		return accessKey;
	}

	/**
	 * Setter method for property <tt>accessKey</tt>.
	 *
	 * @param accessKey
	 *            value to be assigned to property accessKey
	 */
	public final void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

}
