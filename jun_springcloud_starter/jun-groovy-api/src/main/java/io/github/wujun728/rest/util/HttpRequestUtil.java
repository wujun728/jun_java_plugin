package io.github.wujun728.rest.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.StaticLog;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.*;
import java.util.regex.Pattern;

/**
 * HTTP请求工具类
 * 整合路径参数、URL参数、表单、JSON Body、Header、Cookie、Session等所有维度的参数解析
 * 兼容原有老方法，新增统一参数解析能力
 *
 * @author wujun
 */
@Slf4j
@Component
public class HttpRequestUtil {

    // 线程本地存储请求对象
    private static final ThreadLocal<HttpServletRequest> requests = ThreadLocal.withInitial(() -> null);

    // ==================== 核心新方法（整合所有参数解析能力） ====================

    /**
     * 解析请求中所有维度的参数（整合版核心方法）
     * 包含：路径参数、URL参数、表单、JSON Body、Header、Cookie、Session、IP、URI等
     *
     * @param request      HTTP请求对象
     * @param pathParams   路径参数（如@PathVariable解析的参数）
     * @return 合并后的所有参数
     */
    public static Map<String, Object> parseAllParams(HttpServletRequest request, Map<String, String> pathParams) {
        // 初始化参数Map，保持插入顺序
        Map<String, Object> allParams = new LinkedHashMap<>();
        setRequest(request);

        // 1. 路径参数
        if (MapUtil.isNotEmpty(pathParams)) {
            allParams.putAll(convertNumberMap(pathParams));
        }

        // 2. URL查询参数
        Map<String, Object> urlParams = getQueryString(request);
        if (MapUtil.isNotEmpty(urlParams)) {
            allParams.putAll(urlParams);
        }

        // 3. 表单参数
        Map<String, Object> formParams = getParameterMap(request);
        if (MapUtil.isNotEmpty(formParams)) {
            allParams.putAll(formParams);
        }

        // 4. JSON Body参数
        Map<String, Object> jsonParams = getHttpJsonParams(request);
        if (MapUtil.isNotEmpty(jsonParams)) {
            allParams.putAll(jsonParams);
        }

        // 5. Header参数（添加header_前缀避免冲突）
        Map<String, Object> headerParams = buildHeaderParams(request);
        if (MapUtil.isNotEmpty(headerParams)) {
            Map<String, Object> prefixHeaderParams = new HashMap<>();
            headerParams.forEach((k, v) -> prefixHeaderParams.put("header_" + k.toLowerCase().replace("-", "_"), v));
            allParams.putAll(prefixHeaderParams);
        }

        // 6. Cookie参数（添加cookie_前缀避免冲突）
        Map<String, Object> cookieParams = getCookieMap(request);
        if (MapUtil.isNotEmpty(cookieParams)) {
            Map<String, Object> prefixCookieParams = new HashMap<>();
            cookieParams.forEach((k, v) -> prefixCookieParams.put("cookie_" + k.toLowerCase(), v));
            allParams.putAll(prefixCookieParams);
        }

        // 7. Session参数
        Map<String, Object> sessionParams = buildSessionParams(request);
        if (MapUtil.isNotEmpty(sessionParams)) {
            allParams.putAll(sessionParams);
        }

        // 8. 基础信息
        allParams.put("uri", request.getRequestURI());
        allParams.put("ip", getIp(request));

        // 统一转换数字类型
        allParams.replaceAll((k, v) -> convertNumber(v));

        return allParams;
    }

    /**
     * 解析所有参数（无路径参数）
     */
    public static Map<String, Object> parseAllParams(HttpServletRequest request) {
        return parseAllParams(request, new HashMap<>());
    }

    /**
     * 获取当前请求上下文的所有参数
     */
    public static Map<String, Object> parseCurrentRequestAllParams(Map<String, String> pathParams) {
        return parseAllParams(getHttpServletRequest(), pathParams);
    }

    /**
     * 获取当前请求上下文的所有参数（无路径参数）
     */
    public static Map<String, Object> parseCurrentRequestAllParams() {
        return parseCurrentRequestAllParams(new HashMap<>());
    }

    /**
     * 手动解析Cookie为Map
     */
    private static Map<String, Object> getCookieMap(HttpServletRequest request) {
        Map<String, Object> cookieMap = new HashMap<>();
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
     * 转换Map中所有值为数字类型（如有必要）
     */
    private static Map<String, Object> convertNumberMap(Map<String, String> sourceMap) {
        Map<String, Object> targetMap = new HashMap<>();
        sourceMap.forEach((k, v) -> targetMap.put(k, convertNumber(v)));
        return targetMap;
    }

    // ==================== 原有老方法（标记过时，内部调用新方法） ====================

    /**
     * 获取当前HTTP请求对象
     * @deprecated 建议使用更语义化的方法名，后续会移除
     */
    @Deprecated
    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    /**
     * 判断是否为AJAX请求
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String accept = request.getHeader("accept");
        String xRequestedWith = request.getHeader("X-Requested-With");
        return ((accept != null && accept.contains("application/json")
                || (xRequestedWith != null && xRequestedWith.contains("XMLHttpRequest"))));
    }

    /**
     * 获取线程内的请求对象
     * @deprecated 建议使用 getHttpServletRequest()，后续会移除
     */
    @Deprecated
    public static HttpServletRequest getRequest() {
        return requests.get();
    }

    /**
     * 设置线程内的请求对象
     * @deprecated 内部使用，后续会移除
     */
    @Deprecated
    public static void setRequest(HttpServletRequest request) {
        requests.set(request);
    }

    /**
     * 获取所有参数（老方法，兼容）
     * @deprecated 建议使用 parseAllParams(HttpServletRequest) 方法
     */
    @Deprecated
    public static Map<String, Object> getAllParameters(HttpServletRequest request) {
        return parseAllParams(request);
    }

    /**
     * 获取表单参数Map
     */
    public static Map<String, Object> getParameterMap(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            Object[] paramValues = request.getParameterValues(paramName);
            if (paramValues.length == 1) {
                Object paramValue = paramValues[0];
                if (StrUtil.isNotBlank(String.valueOf(paramValue))) {
                    map.put(paramName, convertNumber(paramValue));
                }
            }
        }
        return map;
    }

    /**
     * 转换值为数字类型（如有必要）
     */
    public static Object convertNumber(Object paramValue) {
        String valueStr = String.valueOf(paramValue);
        if (NumberUtil.isInteger(valueStr)) {
            return Integer.parseInt(valueStr);
        } else if (NumberUtil.isLong(valueStr)) {
            return Long.parseLong(valueStr);
        } else if (NumberUtil.isDouble(valueStr)) {
            return Double.parseDouble(valueStr);
        } else {
            return paramValue;
        }
    }

    /**
     * 读取请求体内容
     */
    public static String getBody(ServletRequest request) {
        try {
            BufferedReader reader = request.getReader();
            return IoUtil.read(reader);
        } catch (IOException var2) {
            throw new IORuntimeException(var2);
        }
    }

    /**
     * 解析JSON请求体参数
     */
    public static Map<String, Object> getHttpJsonParams(HttpServletRequest request) {
        try {
            Map<String, Object> params = new HashMap<>();
            String json = getBody(request);
            String jsonContent = json;

            if (JSONUtil.isTypeJSON(jsonContent)) {
                if (JSONUtil.isTypeJSONArray(jsonContent)) {
                    JSONArray jsonArray = JSONUtil.parseArray(jsonContent);
                    traverseJsonArray(jsonArray, "", params);
                }
                if (JSONUtil.isTypeJSONObject(jsonContent)) {
                    JSONObject jsonObject = JSONUtil.parseObj(jsonContent);
                    traverseJsonTree(jsonObject, "", params);
                }
            } else {
                json = URLDecoder.decode(json, CharsetUtil.UTF_8);
                params = getUrlParams2(request, json);
                if (CollectionUtil.isNotEmpty(params)) {
                    StaticLog.info("非JSON格式数据，按URL参数解析：" + jsonContent);
                }
            }
            return params;
        } catch (Exception e) {
            log.error("解析JSON参数失败", e);
            return Maps.newHashMap();
        }
    }

    /**
     * 将URL参数转换为Map（过时方法）
     * @deprecated 内部使用，后续会移除
     */
    @Deprecated
    public static Map getUrlParams2(HttpServletRequest request, String param) {
        StringBuffer url = request.getRequestURL();
        url.append("?").append(param);
        Map<String, Object> result = new HashMap<>();
        MultiValueMap<String, String> urlMvp = UriComponentsBuilder.fromHttpUrl(url.toString()).build().getQueryParams();
        urlMvp.forEach((key, value) -> {
            String firstValue = CollectionUtils.isEmpty(value) ? null : value.get(0);
            result.put(key, convertNumber(firstValue));
        });
        return result;
    }

    /**
     * 构建Header参数Map
     */
    public static Map<String, Object> buildHeaderParams(HttpServletRequest request) {
        Enumeration<String> headerKeys = request.getHeaderNames();
        Map<String, Object> result = new HashMap<>();
        while (headerKeys.hasMoreElements()) {
            String key = headerKeys.nextElement();
            Object value = request.getHeader(key);
            result.put(key, convertNumber(value));
        }
        return result;
    }

    /**
     * 构建Session参数Map
     */
    public static Map<String, Object> buildSessionParams(HttpServletRequest request) {
        Enumeration<String> keys = request.getSession().getAttributeNames();
        Map<String, Object> result = new HashMap<>();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            result.put(key, request.getSession().getAttribute(key));
        }
        return result;
    }

    /**
     * 获取URL查询参数
     */
    public static Map<String, Object> getQueryString(HttpServletRequest request) {
        StringBuffer url = request.getRequestURL();
        if (StrUtil.isNotBlank(request.getQueryString())) {
            url.append("?").append(request.getQueryString());
        }
        Map<String, Object> result = new HashMap<>();
        MultiValueMap<String, String> urlMvp = UriComponentsBuilder.fromHttpUrl(url.toString()).build().getQueryParams();
        urlMvp.forEach((key, value) -> {
            String firstValue = CollectionUtils.isEmpty(value) ? null : value.get(0);
            result.put(key, convertNumber(firstValue));
        });
        return result;
    }

    /**
     * 获取IP地址
     */
    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            if (ip.indexOf(",") != -1) {
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip == null ? "" : ip.trim();
    }

    /**
     * 遍历JSON对象树，解析为扁平Map
     */
    public static void traverseJsonTree(JSONObject jsonObject, String parentKey, Map params) {
        for (String key : jsonObject.keySet()) {
            Object object = jsonObject.get(key);
            if (object instanceof JSONObject) {
                traverseJsonTree((JSONObject) object, parentKey + "." + key, params);
            } else if (object instanceof JSONArray) {
                traverseJsonArray((JSONArray) object, parentKey + "." + key, params);
            } else {
                String longKey = StrUtil.isEmpty(parentKey) ? key : parentKey + "." + key;
                if (Pattern.compile(".*[0-9].*").matcher(longKey).matches()) {
                    params.put(longKey, convertNumber(object));
                } else {
                    params.put(key, convertNumber(object));
                }
            }
        }
    }

    /**
     * 遍历JSON数组，解析为扁平Map
     */
    public static void traverseJsonArray(JSONArray jsonArray, String parentKey, Map params) {
        for (int i = 0; i < jsonArray.size(); i++) {
            Object object = jsonArray.get(i);
            if (object instanceof JSONObject) {
                traverseJsonTree((JSONObject) object, parentKey + "." + i, params);
            } else if (object instanceof JSONArray) {
                traverseJsonArray((JSONArray) object, parentKey + "." + i, params);
            } else {
                params.put(parentKey + "." + i, convertNumber(object));
            }
        }
    }

    /**
     * 转换请求参数为Map（兼容老方法）
     * @deprecated 建议使用 getParameterMap(HttpServletRequest) 方法
     */
    @Deprecated
    public static Map<String, Object> convertDataMap(HttpServletRequest request) {
        return getParameterMap(request);
    }

    /**
     * 获取单个参数值
     */
    public static String getValue(HttpServletRequest request, String paramName) {
        if (request == null) {
            return "";
        }
        String obj = request.getParameter(paramName);
        return obj == null ? "" : obj.trim();
    }

    /**
     * 获取整数类型参数值
     */
    public static int getIntValue(HttpServletRequest request, String paramName) {
        int returnVal = 0;
        String obj = request.getParameter(paramName);
        if (StrUtil.isNotBlank(obj) && isInteger(obj)) {
            returnVal = Integer.parseInt(obj);
        }
        return returnVal;
    }

    /**
     * 判断是否为整数
     */
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * 获取多值参数
     */
    public static String[] getValues(HttpServletRequest request, String paramName) {
        String[] value = request.getParameterValues(paramName);
        return value == null ? new String[]{} : value;
    }

    /**
     * 获取多值参数拼接字符串
     */
    public static String getValuesString(HttpServletRequest request, String paramName) {
        return getValuesString(request, paramName, ",");
    }

    /**
     * 获取多值参数拼接字符串（自定义分隔符）
     */
    public static String getValuesString(HttpServletRequest request, String paramName, String delims) {
        StringBuilder temp = new StringBuilder();
        String[] values = request.getParameterValues(paramName);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                if (i > 0) {
                    temp.append(delims);
                }
                temp.append(values[i].trim());
            }
        }
        return temp.toString();
    }
}
