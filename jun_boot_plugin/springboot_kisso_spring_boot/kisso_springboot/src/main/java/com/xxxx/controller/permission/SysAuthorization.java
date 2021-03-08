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

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.baomidou.kisso.SSOAuthorization;
import com.baomidou.kisso.Token;

/**
 * 
 * 系统权限授权实现类
 *
 */
@Component
public class SysAuthorization implements SSOAuthorization {

	private static List<String> permissionList = new ArrayList<String>();


	static {
		/**
		 * 正常情况，该部分数据从数据库中加载。
		 */
		permissionList.add("1000");
	}


	/**
	 * 
	 * 开启配置 sso.permission.uri=true 支持、先验证 url 地址，后验证注解。
	 * 
	 */
	public boolean isPermitted( Token token, String permission ) {
		/**
		 * 循环判断权限编码是否合法，token 获取登录用户ID信息、判断相应权限也可作为缓存主键使用。
		 */
		for ( String perm : permissionList ) {
			if ( perm.equals(permission) ) {
				return true;
			}
		}
		return false;
	}

}
