package com.jun.plugin.commons.util;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import com.jun.plugin.commons.util.apiext.StringUtil;

public class MessageUtils {
	private static Map<String, ResourceBundle> messagesMap = new HashMap<String, ResourceBundle>();// util包用的message
	private static final String DEFAULTKEY = "default";

	static {
		messagesMap.put(DEFAULTKEY,
				ResourceBundle.getBundle("I18N/MessageBundleUtil"));
	}

	/****
	 * 通过语言得到国际化信息
	 * 
	 * @param lang
	 * @return
	 */
	public static ResourceBundle getResourceBundleByLang(String lang) {
		Locale locale = StringUtil.isNull(lang) ? null : new Locale(lang);
		return getResourceBundleByLang(locale);
	}

	/****
	 * 通过Locale得到国际化信息
	 * 
	 * @param locale
	 * @return
	 */
	public static ResourceBundle getResourceBundleByLang(Locale locale) {
		locale = locale == null ? Locale.getDefault() : locale;
		if (messagesMap.size() > 0
				&& messagesMap.get(locale.getLanguage()) != null) {
			return messagesMap.get(locale.getLanguage());
		} else {
			ResourceBundle ret = ResourceBundle.getBundle(
					"I18N/MessageBundleUtil", locale);
			if (ret == null) {
				ret = messagesMap.get(DEFAULTKEY);
			}
			messagesMap.put(locale.getLanguage(), ret);
			return ret;
		}
	}

	/****
	 * 得到某国际化的对应key的解释
	 * 
	 * @param lang
	 * @param key
	 * @return
	 */
	public static String get(String lang, String key) {
		lang = StringUtil.isNull(lang) ? Locale.getDefault().getLanguage()
				: lang;
		if (StringUtil.isNull(key)) {
			return "";
		}
		ResourceBundle curBundle = getResourceBundleByLang(lang);
		String retstr = curBundle.getString(key);
		if (StringUtil.isNull(retstr)) {
			retstr = messagesMap.get(DEFAULTKEY).getString(key);
		}
		return retstr;
	}

}
