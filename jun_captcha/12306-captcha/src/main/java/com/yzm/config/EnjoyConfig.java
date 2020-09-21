package com.yzm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jfinal.template.ext.spring.JFinalViewResolver;
import com.jfinal.template.source.ClassPathSourceFactory;

/**
 * 
 * http://www.jfinal.com/doc/6-1
 *
 */
@Configuration
public class EnjoyConfig {

	@Bean(name = "jfinalViewResolver")
	public JFinalViewResolver getJFinalViewResolver() {
		JFinalViewResolver jf = new JFinalViewResolver();
		jf.setDevMode(true);
		jf.setSourceFactory(new ClassPathSourceFactory());
		// 在使用 ClassPathSourceFactory 时要使用 setBaseTemplatePath
		// 代替 jfr.setPrefix("/view/")
		jf.setBaseTemplatePath("/template/view/");
		jf.setSuffix(".html");
		jf.setContentType("text/html;charset=UTF-8");
		jf.setOrder(1);
		jf.setSessionInView(true);
		jf.setExposeRequestAttributes(true);
		return jf;
	}
}
