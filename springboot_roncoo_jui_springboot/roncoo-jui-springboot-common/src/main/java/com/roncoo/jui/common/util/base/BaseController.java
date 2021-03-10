/*
 * Copyright 2015-2016 RonCoo(http://www.roncoo.com) Group.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.roncoo.jui.common.util.base;

import java.text.MessageFormat;

import com.roncoo.jui.common.util.JSONUtil;
import com.roncoo.jui.common.util.jui.Jui;

/**
 * 控制基础类，所以controller都应该继承这个类
 * 
 * @author Wujun
 */
public class BaseController extends Base {

	public static final String TEXT_UTF8 = "text/html;charset=UTF-8";
	public static final String JSON_UTF8 = "application/json;charset=UTF-8";
	public static final String XML_UTF8 = "application/xml;charset=UTF-8";

	public static final String LIST = "list";
	public static final String VIEW = "view";
	public static final String ADD = "add";
	public static final String SAVE = "save";
	public static final String EDIT = "edit";
	public static final String UPDATE = "update";
	public static final String DELETE = "delete";
	public static final String PAGE = "page";

	public static String redirect(String format, Object... arguments) {
		return new StringBuffer("redirect:").append(MessageFormat.format(format, arguments)).toString();
	}

	public static String success(String navTabId) {
		return JSONUtil.toJSONString(new Jui(200, navTabId, "操作成功", "closeCurrent"));
	}

	public static String delete(String navTabId) {
		return JSONUtil.toJSONString(new Jui(200, navTabId, "操作成功", ""));
	}

	public static String error(String message) {
		return JSONUtil.toJSONString(new Jui(300, "", message, ""));
	}

}
