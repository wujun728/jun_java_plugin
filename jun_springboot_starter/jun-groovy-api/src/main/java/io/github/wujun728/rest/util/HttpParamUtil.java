package io.github.wujun728.rest.util;


import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.extra.servlet.ServletUtil;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * HTTP参数解析工具类
 * 整合路径参数、URL参数、表单、JSON Body、Header、Cookie等所有维度的参数解析
 */
public class HttpParamUtil {

    /**
     * 解析请求中所有维度的参数
     * @param request HTTP请求对象
     * @param pathParams 路径参数（如@PathVariable解析的参数）
     * @return 合并后的所有参数
     */
    public static Map<String, Object> parseAllParams(HttpServletRequest request, Map<String, String> pathParams) {
        Map<String, Object> allParams = new LinkedHashMap<>();

        // 路径参数
        if (MapUtil.isNotEmpty(pathParams)) {
            allParams.putAll(pathParams);
            printParamLog("路径参数", pathParams);
        }

        // URL查询参数
        Map<String, String> urlParams = ServletUtil.getParamMap(request);
        if (MapUtil.isNotEmpty(urlParams)) {
            allParams.putAll(urlParams);
            printParamLog("URL查询参数", urlParams);
        }

        // Header参数（添加前缀避免冲突）
        Map<String, String> headerParams = ServletUtil.getHeaderMap(request);
        if (MapUtil.isNotEmpty(headerParams)) {
            Map<String, Object> headerMap = new HashMap<>();
            headerParams.forEach((k, v) -> headerMap.put("header_" + k.toLowerCase().replace("-", "_"), v));
            allParams.putAll(headerMap);
            printParamLog("Header参数", headerMap);
        }

        // Cookie参数（手动解析，兼容所有Hutool版本）
        Map<String, String> cookieParams = getCookieMap(request);
        if (MapUtil.isNotEmpty(cookieParams)) {
            Map<String, Object> cookieMap = new HashMap<>();
            cookieParams.forEach((k, v) -> cookieMap.put("cookie_" + k.toLowerCase(), v));
            allParams.putAll(cookieMap);
            printParamLog("Cookie参数", cookieMap);
        }

        // JSON请求体参数
        Map<String, Object> bodyParams = parseJsonBody(request);
        if (MapUtil.isNotEmpty(bodyParams)) {
            allParams.putAll(bodyParams);
            printParamLog("JSON Body参数", bodyParams);
        }

        return allParams;
    }

    /**
     * 解析JSON格式的请求体参数
     */
    private static Map<String, Object> parseJsonBody(HttpServletRequest request) {
        Map<String, Object> bodyParams = new HashMap<>();
        String contentType = request.getContentType();

        if (StrUtil.isBlank(contentType) || !contentType.contains("application/json")) {
            return bodyParams;
        }

        try {
            String bodyStr = readRequestContent(request);
            if (StrUtil.isNotBlank(bodyStr)) {
                bodyParams = JSONUtil.toBean(bodyStr, Map.class);
            }
        } catch (Exception e) {
            throw new RuntimeException("JSON请求体解析失败", e);
        }
        return bodyParams;
    }

    /**
     * 读取请求体内容（兼容所有Hutool版本）
     */
    private static String readRequestContent(HttpServletRequest request) throws IOException {
        StringBuilder content = new StringBuilder();
        // 手动获取请求编码，替代不存在的ServletUtil.getCharset方法
        String charset = request.getCharacterEncoding();
        if (StrUtil.isBlank(charset)) {
            charset = CharsetUtil.UTF_8;
        }

        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        }

        String contentStr = content.toString();
        return StrUtil.isBlank(contentStr) ? "" : new String(contentStr.getBytes(), Charset.forName(charset));
    }

    /**
     * 手动解析Cookie为Map（替代Hutool不存在的getCookieMap方法）
     */
    private static Map<String, String> getCookieMap(HttpServletRequest request) {
        Map<String, String> cookieMap = new HashMap<>();
        Cookie[] cookies = request.getCookies();

        if (cookies == null || cookies.length == 0) {
            return cookieMap;
        }

        for (Cookie cookie : cookies) {
            cookieMap.put(cookie.getName(), cookie.getValue());
        }
        return cookieMap;
    }

    /**
     * 获取当前请求对象
     */
    public static HttpServletRequest getCurrentRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new RuntimeException("未获取到HTTP请求上下文，请确认在请求线程中调用");
        }
        return attributes.getRequest();
    }

    /**
     * 打印参数解析日志
     */
    private static void printParamLog(String paramType, Map<?, ?> params) {
        if (MapUtil.isNotEmpty(params)) {
            System.out.println(String.format("解析%s：%s", paramType, JSONUtil.toJsonStr(params)));
        }
    }

    // 简化调用方法
    public static Map<String, Object> parseAllParams(HttpServletRequest request) {
        return parseAllParams(request, new HashMap<>());
    }

    public static Map<String, Object> parseCurrentRequestAllParams(Map<String, String> pathParams) {
        return parseAllParams(getCurrentRequest(), pathParams);
    }

    public static Map<String, Object> parseCurrentRequestAllParams() {
        return parseCurrentRequestAllParams(new HashMap<>());
    }

    /**
     * 安全获取参数值（修复泛型兼容问题）
     */
    @SuppressWarnings("unchecked")
    public static <T> T getParam(Map<String, Object> allParams, String paramName, T defaultValue) {
        if (MapUtil.isEmpty(allParams) || !allParams.containsKey(paramName)) {
            return defaultValue;
        }

        Object value = allParams.get(paramName);
        try {
            return (T) value;
        } catch (ClassCastException e) {
            // 类型转换失败返回默认值
            return defaultValue;
        }
    }

    // 单独解析各类参数的快捷方法
    public static Map<String, String> parseUrlParams() {
        return ServletUtil.getParamMap(getCurrentRequest());
    }

    public static Map<String, String> parseHeaderParams() {
        return ServletUtil.getHeaderMap(getCurrentRequest());
    }

    public static Map<String, String> parseCookieParams() {
        return getCookieMap(getCurrentRequest());
    }

    public static Map<String, Object> parseJsonBodyParams() {
        return parseJsonBody(getCurrentRequest());
    }
}
