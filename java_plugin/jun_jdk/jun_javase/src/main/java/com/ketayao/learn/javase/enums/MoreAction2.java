package com.ketayao.learn.javase.enums;

public enum MoreAction2 {
	TURN_LEFT {
		// 实现抽象方法
		public String getDescription() {
			return "向左转";
		}
	}, // 记得这里的枚举值分隔使用,

	TURN_RIGHT {
		// 实现抽象方法
		public String getDescription() {
			return "向右转";
		}
	},

	SHOOT {
		// 实现抽象方法
		public String getDescription() {
			return "射击";
		}
	}; // 记得这里的枚举值结束使用;

	// 声明抽象方法
	public abstract String getDescription();
}