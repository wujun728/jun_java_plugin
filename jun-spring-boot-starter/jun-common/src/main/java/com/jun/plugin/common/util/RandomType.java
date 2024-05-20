
package com.jun.plugin.common.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 生成的随机数类型
 *
 * @author L.cm
 */
@Getter
@RequiredArgsConstructor
public enum RandomType {
	/**
	 * INT STRING ALL
	 */
	INT(RandomType.INT_STR),
	STRING(RandomType.STR_STR),
	ALL(RandomType.ALL_STR);

	private final String factor;

	/**
	 * 随机字符串因子
	 */
	private static final String INT_STR = "0123456789";
	private static final String STR_STR = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private static final String ALL_STR = INT_STR + STR_STR;
}
