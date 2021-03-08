package com.zhaodui.springboot.common.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.zhaodui.springboot.common.realm.UserRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro配置
 *
 * @author Wujun
 */
@Configuration
public class ShiroConfig {
	/**
	 * 注解支持
	 *
	 * @param manager 安全管理器
	 * @return 通知器
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager manager) {
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(manager);
		return advisor;
	}

	/**
	 * 注入过滤器工厂
	 *
	 * @param manager 安全管理器
	 * @return 过滤器工厂
	 */
	@Bean
	public ShiroFilterFactoryBean shiroFilter(SecurityManager manager) {
		ShiroFilterFactoryBean factory = new ShiroFilterFactoryBean();
		factory.setSecurityManager(manager);
		factory.setLoginUrl("/login");
		factory.setUnauthorizedUrl("/login");

		Map<String, String> map = new LinkedHashMap<>();

		// 静态文件授权
		map.put("/css/**", "anon");
		map.put("/js/**", "anon");
		map.put("/img/**", "anon");

		// 登录界面不拦截
		map.put("/login", "anon");
		map.put("/doLogin", "anon");

		// 拦截未登录的用户
		map.put("/**", "anon");

		factory.setFilterChainDefinitionMap(map);
		return factory;
	}

	/**
	 * 注入安全管理器
	 *
	 * @return 安全管理器
	 */
	@Bean
	public SecurityManager securityManager(UserRealm userRealm) {
		DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
		manager.setRealm(userRealm);
		return manager;
	}

	/**
	 * 注入用户Realm
	 *
	 * @return 用户Realm
	 */
	@Bean
	public UserRealm userRealm() {
		return new UserRealm();
	}

	/**
	 * 注入Shiro标签
	 *
	 * @return Shiro标签
	 */
	@Bean
	public ShiroDialect shiroDialect() {
		return new ShiroDialect();
	}
}
