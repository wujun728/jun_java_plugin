package com.sam.demo.spring.boot.mvc.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sam.demo.spring.boot.mvc.web.message.converter.CustomMessageConverter;
import com.sam.demo.spring.boot.mvc.web.message.converter.CustomMethodArgumentResolver;

@Configuration
public class WebConfig extends WebMvcConfigurationSupport {
	
	@Bean
	public Gson gson(){
		return new GsonBuilder().create();
	}
	
	/**
	 * 参数解析器配置
	 */
	@Override
	protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(methodArgumentResolver());
	}
	
	private CustomMethodArgumentResolver methodArgumentResolver() {
		CustomMethodArgumentResolver portalMethodArgumentResolver = new CustomMethodArgumentResolver();
		portalMethodArgumentResolver.setGson(gson());
		return portalMethodArgumentResolver;
	}

	/**
	 * 添加自定义配置消息转换器,提供json到自定义对象的转换。用于 @RequestBody 和 @ResponseBody。
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(customMessageConverter());
		super.configureMessageConverters(converters);
	}
		
	private CustomMessageConverter customMessageConverter(){
		CustomMessageConverter customMessageConverter = new CustomMessageConverter();
		customMessageConverter.setGson(gson());
		return customMessageConverter;
	}

	
}
