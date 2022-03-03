package org.springrain.cms.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

public class SiteUtils {

    private volatile static Map<String, Integer> schemeMap = new HashMap<String, Integer>();// 缓存scheme的协议和端口
    static {
        schemeMap.put("http", 80);
        schemeMap.put("https", 443);
        schemeMap.put("ftp", 21);
    }

    private SiteUtils() {
        throw new IllegalAccessError("工具类不能实例化");
    }

    public static String getBaseURL(HttpServletRequest request) {
        String path = request.getContextPath();
        if ("/".equals(path)) {
            path = "";
        }
        String basePath = request.getScheme() + "://" + request.getServerName();
        Integer defaultPort = schemeMap.get(request.getScheme().toLowerCase());
        if (defaultPort == null || request.getServerPort() - defaultPort != 0) {
            basePath = basePath + ":" + request.getServerPort();
        }
        basePath = basePath + path;

        return basePath;
    }

    public static String getRequestURL(HttpServletRequest request) throws Exception {
        StringBuffer uri = request.getRequestURL();
        String param = request.getQueryString();
        if (StringUtils.isNotBlank(param)) {
            uri = uri.append("?").append(param);
        }
        return uri.toString();
    }
}
