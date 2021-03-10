package com.ibeetl.admin.core.rbac;

/**
 * 数据权限算法结果
 * @author Wujun
 *
 */
public enum AccessType {
	OnlyUser(1), OnlyOrg(2), AllOrg(3), NoneOrg(4);

	private int value;

	AccessType(int value) {
		this.value = value;
	}
}
