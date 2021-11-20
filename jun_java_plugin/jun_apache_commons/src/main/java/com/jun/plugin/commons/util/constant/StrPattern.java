package com.jun.plugin.commons.util.constant;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum StrPattern {

	char_normal("^[a-zA-Z0-9_\u4e00-\u9fa5]+$"), // 只含有汉字、数字、字母、下划线，下划线位置不限
	char_zh("[\u4e00-\u9fa5]"), // 匹配中文字符
	row_blank("\\n\\s*\\r"), // 空白行 可以用来删除空白行
	email("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*"), // email
	identity("\\d{15}|\\d{18}"), // 身份证

	date("^[0-9]{4}\\-[0-9]{1,2}\\-[0-9]{1,2}"), // 日期格式１
													// YYYY-M(M)-D(D)如:2005-01-01
	date_time(
			"^([0-9]{4})(-)((0([1-9]{1}))|(1[0-2]))(-)(([0-2]([0-9]{1}))|(3[0|1]))( )(([0-1]([0-9]{1}))|(2[0-4]))(:)([0-5]([0-9]{1}))(:)([0-5]([0-9]{1}))"), // 2006-09-09
																																								// 09:10:22
	mobile_phone("^(13[0-9]|15[0|3|6|7|8|9]|18[6|8|9])\\d{8}$"), // 手机号码
	number_float("^(-?\\d+)(\\.\\d+)?$"), // 数字类型 浮点型
	number_float_positive("^\\d+(\\.\\d+)?$"), // 非负浮点数（正浮点数 + 0）
	number_integer("^-?\\d+$"), // 整数
	number_integer_positive("^\\d+$"); // 非负整数

	private String code;

	private StrPattern(String code) {
		this.code = code;
	}

	/**
	 * 检查字符串格式是否正确
	 * 
	 * @param str
	 *            待检查的字符串
	 * @return boolean
	 */
	public boolean checkStrFormat(String str) {
		Pattern pattern = Pattern.compile(code);
		Matcher matcher = pattern.matcher(str);
		boolean bool = matcher.matches();
		return bool;
	}
}
