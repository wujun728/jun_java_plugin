/**
 * Copyright (c) 2011-2014, hubin (243194995@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.xxxx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.SSOToken;
import com.baomidou.kisso.annotation.Action;
import com.baomidou.kisso.annotation.Login;
import com.baomidou.kisso.web.waf.request.WafRequestWrapper;

/**
 * 登录
 */
@Controller
public class LoginController extends BaseController {

	/**
	 * 登录 （注解跳过权限验证）
	 */
	@Login(action = Action.Skip)
	@RequestMapping("/login")
	public String login() {
		SSOToken st = SSOHelper.getToken(request);
		if (st != null) {
			return redirectTo("/index.html");
		}
		return "/login";
	}

	/**
	 * 登录 （注解跳过权限验证）
	 */
	@Login(action = Action.Skip)
	@RequestMapping("/loginpost")
	public String loginpost() {
		/**
		 * 生产环境需要过滤sql注入
		 */
		WafRequestWrapper req = new WafRequestWrapper(request);
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		if ("kisso".equals(username) && "123".equals(password)) {
			
			/*
			 * authSSOCookie 设置 cookie 同时改变 jsessionId
			 */
			SSOToken st = new SSOToken(request);
			st.setId(12306L);
			st.setUid("12306");
			st.setType(1);
			
			//记住密码，设置 cookie 时长 1 周 = 604800 秒 【动态设置 maxAge 实现记住密码功能】
			//String rememberMe = req.getParameter("rememberMe");
			//if ( "on".equals(rememberMe) ) {
			//	request.setAttribute(SSOConfig.SSO_COOKIE_MAXAGE, 604800); 
			//}
			SSOHelper.setSSOCookie(request, response, st, true);
			
			/*
			 * 登录需要跳转登录前页面，自己处理 ReturnURL 使用 
			 * HttpUtil.decodeURL(xx) 解码后重定向
			 */
			return redirectTo("/index.html");
		}
		return "/login";
	}
}
