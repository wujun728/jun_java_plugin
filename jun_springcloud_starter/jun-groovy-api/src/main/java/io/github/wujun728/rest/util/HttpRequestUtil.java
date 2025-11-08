package io.github.wujun728.rest.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.StaticLog;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 保留用户会话，以方便在业务代码任何地方调用
 *
 * @author wujun
 */
@Slf4j
@Component
public class HttpRequestUtil {


	public static HttpServletRequest getHttpServletRequest() {
		return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
	}

	public static boolean isAjaxRequest(HttpServletRequest request) {
		String accept = request.getHeader("accept");
		String xRequestedWith = request.getHeader("X-Requested-With");
		// 如果是异步请求或是手机端，则直接返回信息
		return ((accept != null && accept.contains("application/json")
				|| (xRequestedWith != null && xRequestedWith.contains("XMLHttpRequest"))
		));
	}


	private static final ThreadLocal<HttpServletRequest> requests = new ThreadLocal<HttpServletRequest>() {
		@Override
		protected HttpServletRequest initialValue() {
			return null;
		}
	};


	public static HttpServletRequest getRequest() {
		return requests.get();
	}


	public static void setRequest(HttpServletRequest request) {
		requests.set(request);
	}


	public static Map<String, Object> getAllParameters(HttpServletRequest request) {
		HttpRequestUtil.setRequest(request);
		String unParseContentType = request.getContentType();

		// 如果是浏览器get请求过来，取出来的contentType是null
		if (unParseContentType == null) {
			unParseContentType = MediaType.APPLICATION_FORM_URLENCODED_VALUE;
		}
		// 解析contentType 格式: appliation/json;charset=utf-8
		String[] contentTypeArr = unParseContentType.split(";");
		String contentType = contentTypeArr[0];
		Map<String, Object> params = Maps.newHashMap();
		// 如果是application/json请求，不管接口规定的content-type是什么，接口都可以访问，且请求参数都以json body 为准

		if (contentType.equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE)) {
			params = getHttpJsonParams(request);
		} else if (contentType.equalsIgnoreCase(MediaType.APPLICATION_FORM_URLENCODED_VALUE)) {
			params = getHttpJsonParams(request);
		} else {
			params = getHttpJsonParams(request);
		}
		String uri = request.getRequestURI();
		Map<String,Object> parameterMap = HttpRequestUtil.getParameterMap(request);
		Map<String, Object> header = HttpRequestUtil.buildHeaderParams(request);
		Map<String, Object> session = HttpRequestUtil.buildSessionParams(request);
		Map<String, Object> urivar = HttpRequestUtil.getQueryString(request);
		String ip = HttpRequestUtil.getIp(request);
		if (!CollectionUtils.isEmpty(parameterMap)) params.putAll(parameterMap);
		if (!CollectionUtils.isEmpty(session)) params.putAll(session);
		if (!CollectionUtils.isEmpty(header)) params.putAll(header);
		if (!CollectionUtils.isEmpty(urivar)) params.putAll(urivar);
		params.put("uri", uri);
		params.put("ip", ip);
		for(String key : params.keySet()){
			params.put(key,convertNumber(params.get(key)));
		}
		return params;
	}


	public static Map<String, Object> getParameterMap(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = paramNames.nextElement();
			Object[] paramValues = request.getParameterValues(paramName);
			if (paramValues.length == 1) {
				Object paramValue = paramValues[0];
				if (String.valueOf(paramValue).length() != 0) {
					map.put(paramName, convertNumber(paramValue));
				}
			}
		}
		return map;
	}

	public static Object convertNumber(Object paramValue){
		if(NumberUtil.isInteger(String.valueOf(paramValue))){
			return Integer.parseInt(String.valueOf(paramValue));
		}else
		if(NumberUtil.isLong(String.valueOf(paramValue))){
			return Long.parseLong(String.valueOf(paramValue));
		}else
		if(NumberUtil.isDouble(String.valueOf(paramValue))){
			return Double.parseDouble(String.valueOf(paramValue));
		}else{
			return paramValue;
		}
	}
	/*@Deprecated
	public static Map<String, Object> getParameters(HttpServletRequest req) {
		Map<String, Object> params = new HashMap<>();
		Map<String, String[]> parameterMap = req.getParameterMap();
		if (parameterMap.isEmpty()) {
			return params;
		}
		Set<String> keys = parameterMap.keySet();
		for (String key : keys) {
			String[] values = parameterMap.get(key);
			if (values.length == 1) {
				params.put(key, values[0]);
			} else {
				params.put(key, values);
			}
		}
		return params;
	}*/

	public static String getBody(ServletRequest request) {
		try {
			BufferedReader reader = request.getReader();
			Throwable var2 = null;

			String var3;
			try {
				var3 = IoUtil.read(reader);
			} catch (Throwable var13) {
				var2 = var13;
				throw var13;
			} finally {
				if (reader != null) {
					if (var2 != null) {
						try {
							reader.close();
						} catch (Throwable var12) {
							var2.addSuppressed(var12);
						}
					} else {
						reader.close();
					}
				}

			}
			return var3;
		} catch (IOException var15) {
			throw new IORuntimeException(var15);
		}
	}

	public static Map<String, Object> getHttpJsonParams(HttpServletRequest request) {
		try {
			Map<String, Object> params = new HashMap<>();
			String json = getBody(request);
			String jsonContent = json;
			if(JSONUtil.isTypeJSON(jsonContent) ){
				//json = StringEscapeUtils.unescapeJson(json);
				/*if(StrUtil.isNotEmpty(json) && json.length()>2 && json.startsWith("\"") && json.endsWith("\"")){
					json = json.substring(1,json.length()-1);
				}*/
				/*JSONObject jsonObject = new JSONObject();
				JSONArray jsonArray = new JSONArray();
				Object jsonObj = JSONUtil.parse(jsonContent);
				if (jsonObj instanceof JSONObject  ||  JSONUtil.isJsonObj(jsonContent) ) {
					jsonObject = (JSONObject) jsonObj;
				} else if (jsonObj instanceof JSONArray || JSONUtil.isJsonArray(jsonContent) ) {
					jsonArray = (JSONArray) jsonObj;
				}else{
					StaticLog.error("非JSON格式数据，无法解析："+jsonContent);
				}*/
				if(JSONUtil.isTypeJSONArray(jsonContent) ){
					JSONArray jsonArray = JSONUtil.parseArray(jsonContent);
					traverseJsonArray(jsonArray, "", params);
				}
				if(JSONUtil.isTypeJSONObject(jsonContent) ){
					JSONObject jsonObject = JSONUtil.parseObj(jsonContent);
					traverseJsonTree(jsonObject, "", params);
				}
				/*if (!ObjectUtils.isEmpty(jsonObject)) {
					traverseJsonTree(jsonObject, "", params);
				} else if (!ObjectUtils.isEmpty(jsonArray)) {
					traverseJsonArray(jsonArray, "", params);
				}*/
			}else{
				json = URLDecoder.decode(json, "UTF-8");
				params = HttpRequestUtil.getUrlParams2(request,json);
				if(CollectionUtil.isNotEmpty(params)){
					StaticLog.info("非JSON格式数据，无法解析："+jsonContent);
				}
			}
			return params;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return Maps.newHashMap();
	}

	//将url参数转换成map
	@Deprecated
	public static Map getUrlParams2(HttpServletRequest request,String param) {
		StringBuffer url = request.getRequestURL();
		url.append("?");
		url.append(param);
		Map<String, Object> result = new HashMap<>();
		MultiValueMap<String, String> urlMvp = UriComponentsBuilder.fromHttpUrl(url.toString()).build().getQueryParams();
		urlMvp.forEach((key, value) -> {
			String firstValue = CollectionUtils.isEmpty(value) ? null : value.get(0);
			NumberUtil.isNumber(firstValue);
			result.put(key, convertNumber(firstValue));
		});
		return result;
	}
	/*public static Map getUrlParams(String param) {
		Map map = new HashMap(0);
		if (StrUtil.isBlank(param)) {
			return map;
		}
		String[] params = param.split("&");
		for (int i = 0; i < params.length; i++) {
			String[] p = params[i].split("=");
			if (p.length == 2) {
				map.put(p[0], p[1]);
			}
		}
		return map;
	}*/


//	@Deprecated
//	public static JSONObject getHttpJsonBody(HttpServletRequest request) {
//		try {
//			InputStreamReader in = new InputStreamReader(request.getInputStream(), "utf-8");
//			BufferedReader br = new BufferedReader(in);
//			StringBuilder sb = new StringBuilder();
//			String line = null;
//			while ((line = br.readLine()) != null) {
//				sb.append(line);
//			}
//			br.close();
//			//String json = StringEscapeUtils.unescapeJson(sb.toString());
//			String jsonContent = JSONUtil.escape(sb.toString());
////			JSONObject jsonObject = JSON.parseObject(jsonContent);
//			JSONObject jsonObject = JSONUtil.parseObj(jsonContent);
//			return jsonObject;
//		} catch (Exception e) {
//			log.error(e.getMessage(), e);
//		}
//		return null;
//	}


//	public static void main111(String[] args) {
//		StringBuilder sb= new StringBuilder();
//		sb.append("\"{\\\"id\\\":\\\"\\\",\\\"tableName\\\":\\\"sys_app\\\",\\\"tablePrefix\\\":\\\"Y\\\",\\\"tableComment\\\":\\\"系统应用表\\\",\\\"className\\\":\\\"App\\\",\\\"busName\\\":\\\"app\\\",\\\"generateType\\\":\\\"1\\\",\\\"appCode\\\":\\\"system\\\",\\\"menuPid\\\":\\\"1264622039642255331\\\",\\\"authorName\\\":\\\"22222\\\",\\\"packageName\\\":\\\"vip.xiaonuo\\\"}\"");
//		String json = sb.toString();
//		System.out.println(json);
//		String jsonContent = JSONUtil.formatJsonStr(json);
//		System.out.println(jsonContent);
//
////		String unescapedJson = json;//StringEscapeUtils.unescapeJson(json);
//		String unescapedJson = StringEscapeUtils.unescapeJson(json);
//		String unescapedJson111 = unescapedJson.substring(1,unescapedJson.length()-1);
//		System.out.println(JSONUtil.isJson(unescapedJson111));
//		System.out.println(unescapedJson111);
//		JSONObject jsonObject = new JSONObject();
//		JSONArray jsonArray = new JSONArray();
//		Object jsonObj = JSONUtil.parse(unescapedJson111);
//		if (jsonObj instanceof JSONObject) {
//			jsonObject = (JSONObject) jsonObj;
//		} else if (jsonObj instanceof JSONArray) {
//			jsonArray = (JSONArray) jsonObj;
//		}
//	}

	public static Map<String, Object> buildHeaderParams(HttpServletRequest request) {
		Enumeration<String> headerKeys = request.getHeaderNames();
		Map<String, Object> result = new HashMap<>();
		while (headerKeys.hasMoreElements()) {
			String key = headerKeys.nextElement();
			Object value = request.getHeader(key);
//			result.put("header."+key, convertNumber(value));
			result.put(""+key, convertNumber(value));
		}
		return result;
	}

	public static Map<String, Object> buildSessionParams(HttpServletRequest request) {
		Enumeration<String> keys = request.getSession().getAttributeNames();
		Map<String, Object> result = new HashMap<>();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
//			result.put("session."+key, request.getSession().getAttribute(key));
			result.put(""+key, request.getSession().getAttribute(key));
		}
		return result;
	}

	public static Map<String, Object> getQueryString(HttpServletRequest request) {
		StringBuffer url = request.getRequestURL();
		if (request.getQueryString() != null) {
			url.append("?");
			url.append(request.getQueryString());
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
	 *
	 * @param request
	 * @return
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


	public static void main1(String[] args) {

		String jsonString = "{\n" +
				"    \"name\":\"John\",\n" +
				"    \"age\":[\n" +
				"  {\n" +
				"    \"id\": \"1668160716561219582\"\n" +
				"  },\n" +
				"  {\n" +
				"    \"id\": \"1668160716561219585\"\n" +
				"  }\n" +
				"],\n" +
				"    \"address\":{\n" +
				"        \"city\":\"New York\",\n" +
				"        \"state\":\"NY\",\n" +
				"        \"zip\":\"10001\",\n" +
				"        \"coordinates\":{\n" +
				"            \"latitude\":40.712776,\n" +
				"            \"longitude\":-74.005974,\n" +
				"            \"accuracy\":5,\n" +
				"            \"details\":{\n" +
				"                \"description\":\"Exact location of the building\"\n" +
				"            }\n" +
				"        },\n" +
				"        \"details\":{\n" +
				"            \"street\":\"123 Main St\",\n" +
				"            \"apt\":\"4B\",\n" +
				"            \"building\":{\n" +
				"                \"name\":\"Central Park Tower\",\n" +
				"                \"floors\":98,\n" +
				"                \"amenities\":{\n" +
				"                    \"gym\":true,\n" +
				"                    \"pool\":true,\n" +
				"                    \"parking\":{\n" +
				"                        \"garage\":true,\n" +
				"                        \"valet\":true\n" +
				"                    }\n" +
				"                }\n" +
				"            }\n" +
				"        }\n" +
				"    }\n" +
				"}";
		Map params = Maps.newHashMap();
		Object jsonObject = JSONUtil.parse(jsonString);
		traverseJsonTree((JSONObject) jsonObject, "", params);
//			traverseJsonArray((JSONArray) jsonObject,"",params);
		StaticLog.info(JSONUtil.toJsonStr(params));
	}

	public static void traverseJsonTree(JSONObject jsonObject, String parentKey, Map params) {
		for (String key : jsonObject.keySet()) {
			Object object = jsonObject.get(key);
			if (object instanceof JSONObject) {
				traverseJsonTree((JSONObject) object, parentKey + "." + key + "", params);
			} else if (object instanceof JSONArray) {
				traverseJsonArray((JSONArray) object, parentKey + "." + key + "", params);
			} else {
				StaticLog.info(key + ":" + object);
				StaticLog.info("---------  " + parentKey + "." + key + ":" + object);
				String longKey;
				if(StrUtil.isEmpty(parentKey)){
					longKey = key;
				}else{
					longKey = parentKey + "." + key;
				}
				if (Pattern.compile(".*[0-9].*").matcher(longKey).matches()) {
					params.put(longKey, object);
				} else {
					params.put(key, object);
				}
			}
		}
	}

	public static void traverseJsonArray(JSONArray jsonArray, String parentKey, Map params) {
		for (int i = 0; i < jsonArray.size(); i++) {
			Object object = jsonArray.get(i);
			if (object instanceof JSONObject) {
				traverseJsonTree((JSONObject) object, parentKey + "." + i + "", params);
			} else if (object instanceof JSONArray) {
				traverseJsonArray((JSONArray) object, parentKey + "." + i + "", params);
			} else {
				//StaticLog.info(object);
				StaticLog.info(parentKey + ":" + i + ":" + object);
			}
		}
	}
//	public static byte[] object2bytes(Object obj) throws Exception {
//		if (obj == null) {
//			return null;
//		}
//		ByteArrayOutputStream bo = new ByteArrayOutputStream();
//		ObjectOutputStream oos = new ObjectOutputStream(bo);
//		oos.writeObject(obj);
//		return bo.toByteArray();
//	}
//	public static Object bytes2object(byte[] bytes) {
//		Object object = null;
//		try {
//			ByteArrayInputStream bis = new ByteArrayInputStream(bytes);//
//			ObjectInputStream ois = new ObjectInputStream(bis);
//			object = ois.readObject();
//			ois.close();
//			bis.close();
//		} catch (IOException ex) {
//			ex.printStackTrace();
//		} catch (ClassNotFoundException ex) {
//			ex.printStackTrace();
//		}
//		return object;
//	}


	public static Map<String, Object> convertDataMap(HttpServletRequest request) {
		Map<String, String[]> properties = request.getParameterMap();
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Iterator<?> entries = properties.entrySet().iterator();
		Map.Entry<?, ?> entry;
		String name = "";
		String value = "";
		while (entries.hasNext()) {
			entry = (Map.Entry<?, ?>) entries.next();
			name = (String) entry.getKey();
			Object valueObj = entry.getValue();
			if (null == valueObj) {
				value = "";
			} else if (valueObj instanceof String[]) {
				String[] values = (String[]) valueObj;
				for (int i = 0; i < values.length; i++) {
					value = values[i] + ",";
				}
				value = value.substring(0, value.length() - 1);
			} else {
				value = valueObj.toString();
			}
			returnMap.put(name, value);
		}
		return returnMap;
	}

	public static String getValue(HttpServletRequest request, String paramName) {
		if(request == null) {
			return "";
		}

		String obj = request.getParameter(paramName);
		if (obj == null) {
			obj = "";
		}
		return obj.trim();
	}

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
	 * @param str 传入的字符串
	 * @return 是整数返回true,否则返回false
	 */
	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		return pattern.matcher(str).matches();
	}

	public static String[] getValues(HttpServletRequest request, String paramName) {
		String[] value;
		value = request.getParameterValues(paramName);
		if (value == null)
			value = new String[] {};
		return value;
	}

	public static String getValuesString(HttpServletRequest request, String paramName) {
		return getValuesString(request, paramName, ",");
	}

	public static String getValuesString(HttpServletRequest request, String paramName,
										 String delims) {
		String temp = "";
		String[] values = request.getParameterValues(paramName);
		if (values == null) {
			return "";
		} else {
			for (int i = 0; i < values.length; i++) {
				if (i == values.length - 1) {
					temp += values[i].trim();
				} else {
					temp += values[i].trim() + delims;
				}
			}
		}
		return temp;
	}


}
