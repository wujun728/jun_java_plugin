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

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.kisso.SSOConfig;
import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.SSOToken;
import com.baomidou.kisso.annotation.Action;
import com.baomidou.kisso.annotation.Login;

/**
 * 首页
 */
@Controller
public class IndexController extends BaseController {

	/**
	 * <p>
	 * SSOHelper.getToken(request)
	 * 
	 * 从 Cookie 解密 token 使用场景，拦截器
	 * </p>
	 * 
	 * <p>
	 * SSOHelper.attrToken(request)
	 * 
	 * 非拦截器使用减少二次解密
	 * </p>
	 */
	@RequestMapping("/index")
	public String index(Model model) {
		SSOToken st = SSOHelper.attrToken(request);
		if (st != null) {
			System.err.println(" Long 类型 ID: " + st.getId());
			model.addAttribute("userId", st.getUid());
		}
		System.err.println(" 启动注入测试模式：" + SSOConfig.getInstance().getRunMode());
		return "index";
	}
	
	/**
	 * 验证码 （注解跳过权限验证）
	 */
	@Login(action = Action.Skip)
	@ResponseBody
	@RequestMapping("/verify")
	public void verify() {
		try {
			String verifyCode = CaptchaUtil.outputImage(response.getOutputStream());
			System.out.println("验证码:" + verifyCode);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 异常 404 提示页
	 */
	@RequestMapping("/404")
	public String error_404() {
		return "error/404";
	}

}
