package com.jun.plugin.commons.util.callback.impl;

import java.util.Locale;

import com.jun.plugin.commons.util.MessageUtils;
import com.jun.plugin.commons.util.callback.IConvertValue;

/****
 * commons-util的国际化转换器
 * 
 * @author Administrator
 * 
 */
public class ConvertValueMsg implements IConvertValue {
	private final String lang;

	public ConvertValueMsg(String lang) {
		this.lang = lang;
	}

	public ConvertValueMsg() {
		this.lang = Locale.getDefault().getLanguage();
	}

	@Override
	public String getStr(String key) {
		return MessageUtils.get(lang, key);
	}

}
