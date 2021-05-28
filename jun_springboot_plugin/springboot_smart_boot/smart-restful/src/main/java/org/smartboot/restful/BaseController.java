package org.smartboot.restful;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.smartboot.service.api.ApiAuthBean;
import org.smartboot.service.api.ApiCodeEnum;
import org.smartboot.service.api.RestApiResult;
import org.smartboot.service.api.RestApiService;
import org.smartboot.service.facade.result.ServiceResult;
import org.smartboot.sosa.core.json.JsonUtil;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;

/**
 * smartboot Controller抽象类
 * 
 * @author Wujun
 * @version BaseController.java, v 0.1 2015年11月5日 上午11:47:40 Seer Exp.
 */
public abstract class BaseController {
	protected static final String SUCCESS_FLAG = "success";

	protected void writeSuccess(ModelMap modelMap, Object data) {
		modelMap.put(SUCCESS_FLAG, true);
		modelMap.put("data", data);
	}

	protected void writeError(ModelMap modelMap, String message) {
		modelMap.put(SUCCESS_FLAG, false);
		modelMap.put("msg", message);
	}

	protected <T> void writeData(ModelMap modelMap, ServiceResult<T> result) {
		if (result.isSuccess())
			writeSuccess(modelMap, result.getData());
		else
			writeError(modelMap, result.getMessage());
	}

	protected ModelAndView executeAndReturnModel(RestApiService service, HttpServletRequest request,
		ApiAuthBean authBean) {
		Enumeration<String> names = request.getParameterNames();
		Map<String, String> requestMap = new HashMap<String, String>();
		while (names.hasMoreElements()) {
			String key = names.nextElement();
			requestMap.put(key, request.getParameter(key));
		}
		RestApiResult<Object> result = service.execute(authBean, requestMap);
		ModelAndView mv = new ModelAndView();
		mv.addObject("code", result.getCode());
		mv.addObject("version", result.getVersion());
		if (result.getCode() == ApiCodeEnum.SUCCESS.getCode()) {
			Object data = result.getData();
			if (data instanceof JSONArray || data == null || data.getClass().isPrimitive()
				|| data.getClass().getName().startsWith("java.lang")) {
				mv.addObject("data", data);
			} else {
				mv.addObject("data", JsonUtil.getJsonObject(data));
			}
		} else {
			mv.addObject("message", result.getMessage());
		}
		return mv;
	}
	
	/**
	 * 获取Cookie
	 * @param name
	 * @param request
	 * @return
	 */
	protected Cookie getCookie(String name, HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (ArrayUtils.isEmpty(cookies) || StringUtils.isEmpty(name)) {
			return null;
		}
		for (Cookie c : cookies) {
			if (StringUtils.equals(c.getName(), name)) {
				return c;
			}
		}
		return null;
	}
}