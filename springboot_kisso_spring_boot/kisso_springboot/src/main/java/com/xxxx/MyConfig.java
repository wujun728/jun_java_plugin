/**
 * Copyright (c) 2011-2014, 等待美丽的花儿  E-mail: 417791635@qq.com
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
package com.xxxx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.baomidou.kisso.SSOAuthorization;
import com.baomidou.kisso.web.WebKissoConfigurer;
import com.baomidou.kisso.web.interceptor.SSOPermissionInterceptor;
import com.baomidou.kisso.web.interceptor.SSOSpringInterceptor;

/** 
* @author Wujun
* @version 创建时间：2016年9月30日 下午3:13:01 
* springboot 配置文件
*/
@Configuration
public class MyConfig extends WebMvcConfigurerAdapter{

	
	@Autowired 
    private ApplicationContext context; 
	//--------------Kisso start--------------------------
    @Bean(initMethod="initKisso")
    public WebKissoConfigurer kissoInit(){
    	WebKissoConfigurer webKissoConfigurer = new WebKissoConfigurer();
    	webKissoConfigurer.setSsoPropPath("properties/sso.properties");
    	return webKissoConfigurer;
    }
    @Bean
    public SSOPermissionInterceptor getSSOPermissionInterceptor(SSOAuthorization authorization){
    	SSOPermissionInterceptor ssoPermissionInterceptor =new  SSOPermissionInterceptor();
    	ssoPermissionInterceptor.setIllegalUrl("http://sso.test.com:8001/test/permission/illegalAccess.html");
    	ssoPermissionInterceptor.setAuthorization(authorization);
    	return ssoPermissionInterceptor;
    }
    //--------------Kisso end--------------------------
    
    /**
     * 配置拦截器
     */
    @Override
	public void addInterceptors(InterceptorRegistry registry) {
    	registry.addInterceptor(new SSOSpringInterceptor()).addPathPatterns("/**");
    	registry.addInterceptor(context.getBean(SSOPermissionInterceptor.class)).addPathPatterns("/test/permission/**");
	}
    /**
     * 静态资源过滤
     */
    @Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
	}


}
