package com.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jfinal.template.ext.spring.JFinalViewResolver;
import com.jfinal.template.source.ClassPathSourceFactory;

/**
 * 模板配置（替代JSP）
 * 
 * @author lenovo
 *
 */
@Configuration
public class EnjoyConfig {

	@Bean(name = "jfinalViewResolver")
	public JFinalViewResolver getJFinalViewResolver() {
		JFinalViewResolver jf = new JFinalViewResolver();
		jf.setDevMode(true);
		jf.setSourceFactory(new ClassPathSourceFactory());
		jf.setBaseTemplatePath("/template/");
		jf.setPrefix("view/");
		jf.setSuffix(".html");
		jf.setContentType("text/html;charset=UTF-8");
		jf.setOrder(0);
		jf.setSessionInView(true);
		jf.setExposeRequestAttributes(true);
		jf.setExposeRequestAttributes(true);
		return jf;
	}
}
