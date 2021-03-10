package org.smartboot.component.cache;

/**
 * 缓存前缀枚举
 *
 * @author Wujun
 * @version v0.1 2015年11月06日 下午1:46 Seer Exp.
 */
public enum PrefixEnum {

	/** 租户权限 */
	TENANT_INFO("tenant", "info");
	/** 缓存前缀所属域 */
	private String domain;

	/** 缓存前缀值 */
	private String value;

	/**
	 * constructor
	 *
	 * @param domain
	 * @param value
	 */
	PrefixEnum(String domain, String value) {
		this.domain = domain;
		this.value = value;
	}

	/**
	 * getter
	 *
	 * @return
	 */
	public String getDomain() {
		return domain;
	}

	/**
	 * getter
	 *
	 * @return
	 */
	public String getValue() {
		return value;
	}

	/**
	 * 获得完整前缀
	 *
	 * @return
	 */
	public String getPrefix() {
		return domain + CacheInterceptor.KEY_SEPERATOR + value;
	}
}
