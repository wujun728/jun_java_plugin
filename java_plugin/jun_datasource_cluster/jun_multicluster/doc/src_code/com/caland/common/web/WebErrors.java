package com.caland.common.web;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class WebErrors {
	/**
	 * email正则表达式
	 */

	public static final Pattern EMAIL_PATTERN = Pattern
			.compile("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$");
	
	/**
	 * phone正则表达式
	 */
	public static final Pattern PHONE_PATTERN = Pattern
			.compile("^0?(13[0-9]|15[012356789]|18[0-9]|14[57])[0-9]{8}$");
	
	public static final String ERROR_EMAIL = "邮箱格式不正确";
	public static final String ERROR_REQUIRED = "此处不能为空";
	
	public static void main(String[] args) {
		WebErrors we = new WebErrors();
		
		boolean vp =we.ifNotPhone("13465379415");
		System.out.println(vp);
		
//		boolean ve =we.ifNotEmail("xxx@126.com");
//		System.out.println(ve);
	}
	
	/**
	 * 验证
	 * @param email
	 * @param field
	 * @param maxLength
	 * @return
	 */
	public boolean ifNotEmail(String email) {
		if (!ifBlank(email)) {
			return false;
		}
		Matcher m = EMAIL_PATTERN.matcher(email);
		if (!m.matches()) {
			addErrorCode(ERROR_EMAIL);
			return false;
		}
		return true;
	}

	public boolean ifNotPhone(String phone) {
		Matcher matcher = PHONE_PATTERN.matcher(phone);
		if (matcher.matches()) {
			return true;
		}
		return false;
	}

	public boolean ifBlank(String s) {
		if (StringUtils.isBlank(s)) {
			addErrorCode(ERROR_REQUIRED);
			return false;
		}
		return true;
	}
	public boolean ifMaxLength(String s,int maxLength) {
		if (s != null && s.length() > maxLength) {
//			addErrorCode("error.maxLength",maxLength);
			return true;
		}
		return false;
	}
	/**
	 * 添加错误代码
	 * 
	 * @param code
	 *            错误代码
	 */
	public void addErrorCode(String code) {
		getErrors().add(code);
	}
	/**
	 * 错误列表
	 * 
	 * @return
	 */
	public List<String> getErrors() {
		if (errors == null) {
			errors = new ArrayList<String>();
		}
		return errors;
	}
	private List<String> errors;
}
