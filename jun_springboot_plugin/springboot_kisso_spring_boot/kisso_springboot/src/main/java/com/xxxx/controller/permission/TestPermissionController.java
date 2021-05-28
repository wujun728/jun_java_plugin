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
package com.xxxx.controller.permission;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.annotation.Action;
import com.baomidou.kisso.annotation.Permission;
import com.xxxx.controller.BaseController;

/**
 * <p>
 * 测试权限的控制器
 * </p>
 * <p>
 * 配置查看（ SSOPermissionInterceptor 拦截器部分 ）：
 * /kisso_springmvc/src/main/resources/spring/servlet-context.xml
 * </p>
 */
@Controller
@RequestMapping("/test/permission")
public class TestPermissionController extends BaseController {

	/**
	 * 
	 * http://sso.test.com:8080/test/permission/index.html
	 * 
	 * <p>
	 * SysAuthorization 初始化该权限，可访问。
	 * </p>
	 */
	@Permission("1000")
	@ResponseBody
	@RequestMapping("/index")
	public String index( Model model ) {
		return "hi " + SSOHelper.attrToken(request).getUid();
	}
	
	/**
	 * 
	 * http://sso.test.com:8080/test/permission/userinfo.html
	 * 
	 * <p>
	 * SysAuthorization 未初始化该权限，因此无法访问。
	 * </p>
	 * <p>
	 * 	1、无权限默认 403 无法访问。
	 * 	2、配置 illegalUrl 跳转地址，重定向至该地址页。
	 * </p>
	 */
	@Permission("1001")
	@ResponseBody
	@RequestMapping("/userinfo")
	public String userinfo( Model model ) {
		return "hi kisso.";
	}
	
	/**
	 * <p>
	 * 非法权限，此处跳过权限验证
	 * </p>
	 */
	@Permission(action = Action.Skip)
	@ResponseBody
	@RequestMapping("/illegalAccess")
	public String illegalAccess( Model model ) {
		return "illegal access";
	}

}
