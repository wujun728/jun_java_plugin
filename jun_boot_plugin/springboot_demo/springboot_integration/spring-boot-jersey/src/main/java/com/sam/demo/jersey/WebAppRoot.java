package com.sam.demo.jersey;

import javax.servlet.Filter;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.sam.demo.jersey.filter.CheckFilter;
import com.sam.demo.jersey.filter.RequestLoggingFilter;

@SpringBootApplication
public class WebAppRoot extends SpringBootServletInitializer {
	
	@Bean
	public Filter characterEncodingFilter() {
		return new CharacterEncodingFilter("UTF-8", true);
	}
	
	@Bean
	public FilterRegistrationBean checkFilter() {
		CheckFilter checkFilter = new CheckFilter();
		FilterRegistrationBean registration = new FilterRegistrationBean(checkFilter);
        registration.addUrlPatterns("/*");
        return registration;
	}
	
	@Bean
	public FilterRegistrationBean requestLoggingFilter() {
		RequestLoggingFilter requestLoggingFilter = new RequestLoggingFilter();
		FilterRegistrationBean registration = new FilterRegistrationBean(requestLoggingFilter);
        registration.addUrlPatterns("/*");
        return registration;
	}
}
