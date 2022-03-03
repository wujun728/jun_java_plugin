package org.springrain.frame.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * cookie工具类
 * 
 * @author caomei
 *
 */
public class CookieUtils {
    private CookieUtils() {
        throw new IllegalAccessError("工具类不能实例化");
    }

    /**
     * 获取cookie值
     * 
     * @param request
     * @param key
     * @return
     */
    public static String getCookieValue(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equalsIgnoreCase(key)) { // 获取键
                return cookie.getValue(); // 获取值
            }
        }

        return null;
    }
}
