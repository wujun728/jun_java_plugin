package com.buxiaoxia.system;

import com.buxiaoxia.system.config.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by xw on 2017/1/5.
 * 2017-01-05 17:23
 */
@Configuration
public class ApplicationConfig extends WebMvcConfigurerAdapter {

	@Autowired
	private JwtFilter jwtFilter;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(jwtFilter).
				addPathPatterns("/**").
				excludePathPatterns("/**/register","/**/token","/**/refresh-token");
	}
}
