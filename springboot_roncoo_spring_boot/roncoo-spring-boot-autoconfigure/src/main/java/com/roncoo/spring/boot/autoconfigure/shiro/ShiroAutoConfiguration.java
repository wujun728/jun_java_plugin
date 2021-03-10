package com.roncoo.spring.boot.autoconfigure.shiro;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author Wujun
 */
@Configuration
@ConditionalOnClass(LifecycleBeanPostProcessor.class)
@EnableConfigurationProperties(ShiroProperties.class)
public class ShiroAutoConfiguration {

	private final ShiroProperties properties;

	public ShiroAutoConfiguration(ShiroProperties properties) {
		this.properties = properties;
	}

	/**
	 * ShiroRealm
	 */
	@Bean(name = "shiroRealm")
	@ConditionalOnMissingBean(ShiroRealm.class)
	public ShiroRealm shiroRealm() {
		ShiroRealm realm = new ShiroRealm();
		return realm;
	}

	/**
	 * SecurityManager
	 */
	@Bean(name = "securityManager")
	@ConditionalOnMissingBean(DefaultWebSecurityManager.class)
	public DefaultWebSecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(shiroRealm());
		return securityManager;
	}

	/**
	 * ShiroFilterFactoryBean
	 */
	@Bean(name = "ShiroFilterFactoryBean")
	@ConditionalOnMissingBean(ShiroFilterFactoryBean.class)
	public ShiroFilterFactoryBean shiroFilterFactoryBean() {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager());
		shiroFilterFactoryBean.setLoginUrl(properties.getLoginUrl());
		shiroFilterFactoryBean.setSuccessUrl(properties.getSuccessUrl());
		shiroFilterFactoryBean.setUnauthorizedUrl(properties.getUnauthorizedUrl());
		Map<String, String> map = properties.getFilterChainDefinitionMap();
		Map<String, String> filterChainDefinitionMap = new HashMap<>();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			if (entry.getValue().contains(",")) {
				String[] strings = entry.getValue().split(",");
				for (String s : strings) {
					filterChainDefinitionMap.put(s, entry.getKey());
				}
			} else {
				filterChainDefinitionMap.put(entry.getValue(), entry.getKey());
			}
		}
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}

	/**
	 * DefaultAdvisorAutoProxyCreator
	 */
	@Bean(name = "defaultAdvisorAutoProxyCreator")
	@ConditionalOnMissingBean(DefaultAdvisorAutoProxyCreator.class)
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
		return defaultAdvisorAutoProxyCreator;
	}

	/**
	 * AuthorizationAttributeSourceAdvisor
	 */
	@Bean(name = "authorizationAttributeSourceAdvisor")
	@ConditionalOnMissingBean(AuthorizationAttributeSourceAdvisor.class)
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
		return authorizationAttributeSourceAdvisor;
	}

	/**
	 * LifecycleBeanPostProcessor
	 */
	@Bean(name = "lifecycleBeanPostProcessor")
	@ConditionalOnMissingBean(ShiroFilterFactoryBean.class)
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

}
