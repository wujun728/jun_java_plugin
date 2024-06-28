package io.github.wujun728.activerecord.utils;


/**
 * Record 转 bean 或者 bean 转 Record 的字段策略
 *
 * 
 */
public enum FieldStrategy {

	/**
	 * 不处理
	 */
	NONE {
		@Override
		public String convert(String name) {
			return name;
		}
	},

	/**
	 * 驼峰转下滑线
	 */
	CAMEL_TO_LOWER {
		@Override
		public String convert(String name) {
			if (name == null) {
				return null;
			}
			char[] charArray = name.toCharArray();
			StringBuilder builder = new StringBuilder();
			for (int i = 0, l = charArray.length; i < l; i++) {
				if (charArray[i] >= CharPool.UPPER_A && charArray[i] <= CharPool.UPPER_Z) {
					builder.append(CharPool.UNDERSCORE).append(charArray[i] += 32);
				} else {
					builder.append(charArray[i]);
				}
			}
			return builder.toString();
		}
	},

	/**
	 * 下划线转驼峰
	 */
	LOWER_TO_CAMEL {
		@Override
		public String convert(String name) {
			if (name == null) {
				return null;
			}
			// 没有下划线直接返回
			if (name.indexOf(CharPool.UNDERSCORE) == -1) {
				return name;
			}
			char[] charArray = name.toCharArray();
			StringBuilder builder = new StringBuilder();
			boolean isUnderlineBefore = false;
			for (char c : charArray) {
				if (CharPool.UNDERSCORE == c) {
					isUnderlineBefore = true;
				} else if (isUnderlineBefore && c >= CharPool.LOWER_A && c <= CharPool.LOWER_Z) {
					builder.append(c -= 32);
					isUnderlineBefore = false;
				} else {
					builder.append(c);
				}
			}
			return builder.toString();
		}
	};

	/**
	 * 转换
	 *
	 * @param name 属性名
	 * @return 属性名
	 */
	public abstract String convert(String name);

}
