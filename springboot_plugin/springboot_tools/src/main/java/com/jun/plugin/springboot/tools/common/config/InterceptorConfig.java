package com.jun.plugin.springboot.tools.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.jun.plugin.springboot.tools.common.interceptor.AuthInterceptor;

/**
 * 拦截器配置
 *
 * @author Wujun
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new AuthInterceptor())
				.addPathPatterns("/**");
	}
}
