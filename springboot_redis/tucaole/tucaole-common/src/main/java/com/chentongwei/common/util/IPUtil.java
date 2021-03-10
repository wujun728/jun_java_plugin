package com.chentongwei.common.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: IP工具类
 */
public final class IPUtil {
    private IPUtil() {}

    /**
     * 获取ip地址，
     * 取值顺序是
     * <ul>
     *     <li>从header中取值，该值由nginx提供，此值由</li>
     *     <li>从getRemoteAddr中取值，此处永远会有值，但当nginx代理时此值为127.0.0.1，此值主要用于开发环境</li>
     * </ul>
     *
     * @param request request
     * @return ip ip地址
     */
    public static final String getIP(HttpServletRequest request) {
        String realIP = request.getHeader("X-Real-IP");
        if (realIP != null) {
            return realIP;
        }
        return request.getRemoteAddr();
    }
}
