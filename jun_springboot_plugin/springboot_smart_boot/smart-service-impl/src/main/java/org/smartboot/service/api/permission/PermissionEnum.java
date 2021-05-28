package org.smartboot.service.api.permission;

/**
 * 权限枚举
 * 
 * @author Wujun
 *
 */
public enum PermissionEnum {

	/** 用户信息新增 */
	USER_INFO_INSERT("USER_INFO_INSERT", "用户信息新增"),
	/** 用户信息删除 */
	USER_INFO_DELETE("USER_INFO_DELETE", "用户信息删除"),
	/** 用户信息查询 */
	USER_INFO_QUERY("USER_INFO_QUERY", "用户信息查询 "),
	/** 用户信息修改 */
	USER_INFO_UPDATE("USER_INFO_UPDATE", "用户信息修改"),
	/** 禁用 */
	SYSTEM_DISABLE("SYSTEM_DISABLE", "禁用");

	PermissionEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}

	private String code;
	private String name;

	public String getCode() {
		return code;
	}

	void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	void setName(String name) {
		this.name = name;
	}

	public static PermissionEnum getPerimission(String code) {
		for (PermissionEnum p : values()) {
			if (p.getCode().equals(code)) {
				return p;
			}
		}
		return null;
	}
}
