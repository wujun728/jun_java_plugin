/**
 * Copyright (c) 2016-~, Bosco.Liao (bosco_liao@126.com).
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
package org.iherus.shiro.tester;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

/**
 * <p>CustomRealm</p>
 * <p>Description:</p>
 * @author Wujun
 * @version 1.0.0
 * @date 2016年4月12日-下午9:24:44
 */
public class CustomRealm extends AuthorizingRealm {

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		String caccount = (String) token.getPrincipal();

		if ("".equals(caccount) || null == caccount) {
			return null;
		}

		// Connects DB, get user data.(production environment)

		// Constructing virtual data.(testing environment)

		final String password = "3d82edb0f2cf95c1fbe636cf124c3618"; //correct password (123456)
		//final String password = "4d82edb0f2cf95c1fbe636cf124c3620"; //incorrect password
		final String salt = "tester";
		final String saccount = salt;
		final String realname = saccount;

		User user = new User(1, saccount, realname, password, salt, 25, null);

		// permissions
		Set<String> permissions = new HashSet<String>();
		permissions.add("user:query");
		permissions.add("user:create");
		permissions.add("user:update");
		permissions.add("user:delete");

		user.setPermissions(permissions);

		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(),
				ByteSource.Util.bytes(user.getSalt()), user.getRealname());

		return info;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		
		User user = (User) principals.getPrimaryPrincipal();
		
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		
		info.addStringPermissions(user.getPermissions());
		
		return info;
	}
	
	/**
	 * Clear Shiro Cache.
	 */
	public void clearCache() {
		PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
		super.clearCache(principals);
	}

}
