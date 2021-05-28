/**
 * 
 */
package com.mall.admin.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



/**
 * @author Wujun
 * @version 1.0
 * @create_at 2017年9月27日下午8:03:45
 */
@Configuration
public class WebAppConfig implements WebMvcConfigurer {

	@Autowired
	private ApiInterceptor apiInterceptor;
	@Autowired
	private AuthInterceptor authInterceptor;
	@Autowired
	private ParamsValidateInterceptor paramsValidateInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(apiInterceptor).addPathPatterns("/**");
		registry.addInterceptor(authInterceptor).addPathPatterns("/**").excludePathPatterns("/system/login");
		registry.addInterceptor(paramsValidateInterceptor).addPathPatterns("/**");
	}
	
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		 //配置静态资源路径
	     registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/resources/");
	}
}
