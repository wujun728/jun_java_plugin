package com.baomidou.springwind.common.enums;

import com.baomidou.framework.common.IEnum;

public enum PermissionType implements IEnum {
	MENU(0, "菜单"), OPERATION(1, "功能");

	private final int key;
	private final String desc;

	PermissionType(final int key, final String desc) {
		this.key = key;
		this.desc = desc;
	}

	@Override
	public int key() {
		return this.key;
	}

	@Override
	public String desc() {
		return this.desc;
	}

}
