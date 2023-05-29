package com.jun.plugin.jdk.enums;

/**
 * 枚举类型既然是类，那么也就可以有构造函数。只不过不得有公开(Public)的构造函数，这是为了避免直接对枚举类型实例化。
 */
public enum DetailActioin2 {
	TURN_LEFT("向左转"), TURN_RIGHT("向右转"), SHOOT("射击");

	private String description;

	// 不公开的构造函数
	private DetailActioin2(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}