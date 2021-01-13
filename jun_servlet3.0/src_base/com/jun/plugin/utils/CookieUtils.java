package com.jun.plugin.utils;

import javax.servlet.http.Cookie;

public class CookieUtils {

	// 根据名称查找指定的cookie
	public static Cookie findCookieByName(Cookie[] cs, String name) {
		if (cs == null || cs.length == 0) {
			return null;
		}

		for (Cookie c : cs) {
			if (c.getName().equals(name)) {
				return c;
			}
		}

		return null;
	}
}
