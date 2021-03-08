package cn.com.bluemoon.common.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import cn.com.bluemoon.common.interceptor.LimitInterceptor;

@Component
public class WebConfig extends WebMvcConfigurerAdapter {

	public void addInterceptors(InterceptorRegistry registry) {
		//多个拦截器组成一个拦截器链
		registry.addInterceptor(new LimitInterceptor(1000, LimitInterceptor.LimitType.DROP)).addPathPatterns("/**");
		super.addInterceptors(registry);
	}
	
}
