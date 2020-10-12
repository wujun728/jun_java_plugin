package com.ketayao.learn.javase.enums;

public enum MoreAction implements IDescription {
	TURN_LEFT {
		// 实现接口上的方法
		public String getDescription() {
			return "向左转";
		}

	}, // 注意这里的枚举值分隔使用,

	TURN_RIGHT {
		// 实现接口上的方法
		public String getDescription() {
			return "向右转";
		}
	}, // 注意这里的枚举值分隔使用,

	SHOOT {
		// 实现接口上的方法
		public String getDescription() {
			return "射击";
		}
	}; // 注意这里的枚举值结束使用;
}