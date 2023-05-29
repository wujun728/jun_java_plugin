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

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleCacheTest {
	
	private static final Logger logger = LoggerFactory.getLogger(SimpleCacheTest.class);
	
	private Subject subject = null;
	
	@Before
	public void init() {
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-realm-test.ini");
		SecurityManager securityManager =  factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		subject = SecurityUtils.getSubject();
	}
	
	@Test
	public void authorization() {
		
		UsernamePasswordToken token = new UsernamePasswordToken("tester", "123456");

		try {
			//Authentication
			this.subject.login(token);
			if (logger.isDebugEnabled()) {
				logger.debug("Authentication result is {}.", this.subject.isAuthenticated());
			}
			final String[] permissions = {"user:create", "user:update"};
			//authorization
			this.subject.checkPermissions(permissions);
			
		} catch (AuthenticationException e1) {
			e1.printStackTrace(); //身份认证异常
		} catch (AuthorizationException e2) {
			e2.printStackTrace();
		}	
	}
	
	@After
	public void destory() {
		//Logout will clear the cache, Please be careful.
		this.subject.logout(); 
	}

}
