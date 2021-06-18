/**
 * Copyright 2015-2016 广州市领课网络科技有限公司
 */
package com.roncoo.jui.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.JsonNode;
import com.roncoo.jui.common.util.ConfUtil;
import com.roncoo.jui.common.util.HttpUtil;
import com.roncoo.jui.common.util.base.BaseController;
import com.roncoo.jui.common.util.jui.Jui;
import com.xiaoleilu.hutool.json.JSONUtil;

/**
 * @author Wujun
 */
@Controller
public class LoginController extends BaseController {

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// 判断是否ajax请求
		if (req.getHeader("x-requested-with") != null && req.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
			// 如果是ajax请求响应头会有x-requested-with
			resp.setCharacterEncoding("UTF-8");
			PrintWriter out = resp.getWriter();
			Jui bj = new Jui();
			bj.setStatusCode(301);
			bj.setMessage("登录超时，请重新登录!");
			out.print(JSONUtil.toJsonStr(bj));
			out.flush();
			return null;
		}
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginPost() throws UnsupportedEncodingException {
		return redirect(login());
	}

	/**
	 * 跳转登录
	 */
	private static String login() throws UnsupportedEncodingException {
		return ConfUtil.getProperty("oauth2AuthorizeUrl").replace("{CLIENTID}", ConfUtil.getProperty("clientId")).replace("{RESPONSETYPE}", "code").replace("{REDIRECTURI}", URLEncoder.encode(ConfUtil.getProperty("redirectUrl"), "utf-8"));
	}

	@RequestMapping(value = "/oauth2", method = RequestMethod.GET)
	public String oauth2(@RequestParam(value = "code", defaultValue = "") String code, @RequestParam(value = "currentIp", defaultValue = "") String currentIp) {
		logger.warn("授权登录：code={},currentIp={}", code, currentIp);
		UsernamePasswordToken token = new UsernamePasswordToken();
		token.setUsername(oauth2(code));
		token.setPassword("www.roncoo.com".toCharArray());
		SecurityUtils.getSubject().login(token);
		return redirect("/index");
	}

	/**
	 * 授权登录
	 * 
	 * @param code
	 */
	private static String oauth2(String code) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("clientId", ConfUtil.getProperty("clientId"));
		param.put("clientSecret", ConfUtil.getProperty("clientSecret"));
		param.put("code", code);
		param.put("grantType", "authorization_code");
		String url = ConfUtil.getProperty("apiAccessTokenUrl");
		JsonNode json = HttpUtil.postForObject(url, param);
		int status = json.get("errCode").asInt();
		if (0 == status) {
			return json.get("resultData").get("roncooNo").asText();
		}
		return "";
	}
	
	@RequestMapping(value = "/timeout", method = { RequestMethod.GET, RequestMethod.POST })
	public String timeout() {
		return "timeout";
	}

}
