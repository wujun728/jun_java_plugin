package org.smartboot.service.api;

import org.apache.commons.lang3.StringUtils;

/**
 * 定义rest api的各正式版本
 * 
 * @author Wujun
 * @version VersionEnum.java, v 0.1 2016年2月10日 下午2:33:38 Seer Exp.
 */
public enum VersionEnum {

	V1("1.0.0", null),
	/** 当前版本 */
	CURRENT_VERSION(V1),;

	private VersionEnum(String version, VersionEnum parent) {
		this.version = version;
		this.parent = parent;
	}

	private VersionEnum(VersionEnum version) {
		this.version = version.version;
		this.parent = version.parent;
	}

	/** 版本号 */
	private String version;
	/** 前一个版本 */
	private VersionEnum parent;

	/**
	 * Getter method for property <tt>version</tt>.
	 *
	 * @return property value of version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Setter method for property <tt>version</tt>.
	 *
	 * @param version
	 *            value to be assigned to property version
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * Getter method for property <tt>parent</tt>.
	 *
	 * @return property value of parent
	 */
	public VersionEnum getParent() {
		return parent;
	}

	/**
	 * Setter method for property <tt>parent</tt>.
	 *
	 * @param parent
	 *            value to be assigned to property parent
	 */
	public void setParent(VersionEnum parent) {
		this.parent = parent;
	}

	/**
	 * 获取指定字符串对应的版本对象
	 * 
	 * @param version
	 * @return
	 */
	public static VersionEnum getVersion(String version) {
		if (StringUtils.isBlank(version))
			return null;
		for (VersionEnum ver : values()) {
			if (StringUtils.equals(ver.version, version)) {
				return ver;
			}
		}
		return null;
	}
}
