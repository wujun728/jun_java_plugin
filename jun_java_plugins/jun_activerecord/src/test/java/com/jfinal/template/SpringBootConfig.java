package com.jfinal.template;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.jfinal.template.ext.spring.JFinalViewResolver;
import com.jfinal.template.source.ClassPathSourceFactory;

/**
 * 整合 Spring Boot
 */
@Configuration
public class SpringBootConfig {
    @Bean(name = "jfinalViewResolver")
    public JFinalViewResolver getJFinalViewResolver() {
        JFinalViewResolver jfr = new JFinalViewResolver();
        // setDevMode 配置放在最前面
        jfr.setDevMode(true);
        
        // 使用 ClassPathSourceFactory 从 class path 与 jar 包中加载模板文件
        jfr.setSourceFactory( new ClassPathSourceFactory() );
        jfr.setPrefix("/view/");
        jfr.setSuffix(".html");
        jfr.setContentType("text/html;charset=UTF-8");
        jfr.setOrder(0);
        jfr.addSharedFunction("/view/common/_layout.html");
        jfr.addSharedFunction("/view/common/_paginate.html");
        return jfr;
    }
}

