package com.jun.plugin.security.util;

import org.springframework.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @date 2021-01-30-11:18
 * @description  Cookie工具类
 **/
public class CookieUtil {
    private static Logger logger = LoggerFactory.getLogger(CookieUtil.class);

    /**
     * 设置 Cookie（生成时间为1天）
     *
     * @param response 响应对象
     * @param name  名称
     * @param value 值
     */
    public static void setCookie(HttpServletResponse response, String name, String value) {
        setCookie(response, name, value, 60 * 60 * 24);
    }

    /**
     * 设置 Cookie
     *
     * @param response 响应对象
     * @param name   名称
     * @param value  值
     * @param path    上下文路径
     */
    public static void setCookie(HttpServletResponse response, String name, String value, String path) {
        setCookie(response, name, value, path, 60 * 60 * 24);
    }

    /**
     * 设置 Cookie
     *
     * @param response 响应对象
     * @param name   名称
     * @param value  值
     * @param maxAge 生存时间（单位秒）
     */
    public static void setCookie(HttpServletResponse response, String name, String value, int maxAge) {
        setCookie(response, name, value, "/", maxAge);
    }

    /**
     * 设置 Cookie
     *
     * @param response 响应对象
     * @param name   名称
     * @param value  值
     * @param maxAge 生存时间（单位秒）
     * @param path   上下文路径
     */
    public static void setCookie(HttpServletResponse response, String name, String value, String path, int maxAge) {
        if (!StringUtils.isEmpty(name)) {
            Cookie cookie = new Cookie(name, null);
            cookie.setPath(path);
            cookie.setMaxAge(maxAge);
            cookie.setHttpOnly(true);
            try {
                cookie.setValue(URLEncoder.encode(value, "utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            response.addCookie(cookie);
        }
    }

    /**
     * 获得指定Cookie的值
     * @param request  请求对象
     * @param name 名称
     * @return 值
     */
    public static String getCookie(HttpServletRequest request, String name) {
        return getCookie(request, null, name, false);
    }

    /**
     * 获得指定Cookie的值，并删除。
     *
     * @param request  请求对象
     * @param response 响应对象
     * @param name 名称
     * @return 值
     */
    public static String getCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        return getCookie(request, response, name, false);
    }

    /**
     * 获得指定Cookie的值
     *
     * @param request  请求对象
     * @param response 响应对象
     * @param name     名字
     * @param path 上下文路径
     * @return 值
     */
    public static String getCookie(HttpServletRequest request, HttpServletResponse response, String name, String path) {
        return getCookie(request, response, name, path, false);
    }

    /**
     * 获得指定Cookie的值
     *
     * @param request  请求对象
     * @param response 响应对象
     * @param name     名字
     * @param isRemove 是否移除
     * @return 值
     */
    public static String getCookie(HttpServletRequest request, HttpServletResponse response, String name, boolean isRemove) {
        return getCookie(request, response, name, "/", isRemove);
    }

    /**
     * 获得指定Cookie的值
     *
     * @param request  请求对象
     * @param response 响应对象
     * @param name     名字
     * @param isRemove 是否移除
     * @return 值
     */
    public static String getCookie(HttpServletRequest request, HttpServletResponse response, String name, String path, boolean isRemove) {
        String value = null;
        if (!StringUtils.isEmpty(name)) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(name)) {
                        try {
                            value = URLDecoder.decode(cookie.getValue(), "utf-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        if (isRemove && response != null) {
                            cookie.setPath(path);
                            cookie.setMaxAge(0);
                            response.addCookie(cookie);
                        }
                    }
                }
            }
        }
        return value;
    }


}