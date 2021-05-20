package com.wang.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicates;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket oauth2() {
    	return new Docket(DocumentationType.SWAGGER_2)
    			.groupName("添加clientDetail")
         .apiInfo(demoApiInfo())
         .select()
         .apis(RequestHandlerSelectors.basePackage("com.wang.controller")) //扫描要提供Api的controller类
         .paths(PathSelectors.regex("/bata/client.*"))//匹配路径
//         .paths(Predicates.not(PathSelectors.regex("/user/userlist.*")))//匹配不包括此路径的api
         .build();
    	 
    }
    
    private ApiInfo demoApiInfo() {
        Contact contact = new Contact("智能springboot项目", "http://my.oschina.net", "taipinshan@qq.com");
        ApiInfo apiInfo = new ApiInfo("用户中心",//大标题
                "REST风格API",//小标题
                "0.1",//版本
                "www.baidu.com",
                contact,//作者
                "下面是api",//链接显示文字
                ""//网站链接
        );
        return apiInfo;
    }
    
    @Bean
    public Docket UserApi() {
    	return new Docket(DocumentationType.SWAGGER_2)
    			.groupName("用户")
         .apiInfo(userApiInfo())
         .select()
         .apis(RequestHandlerSelectors.basePackage("com.smart.controller"))
         .paths(PathSelectors.any())
         .build();
    	 
    }
    
    private ApiInfo userApiInfo() {
        Contact contact = new Contact("智能springboot项目", "http://my.oschina.net", "taipinshan@qq.com");
        ApiInfo apiInfo = new ApiInfo("用户中心",//大标题
                "REST风格API",//小标题
                "0.1",//版本
                "www.baidu.com",
                contact,//作者
                "下面是api",//链接显示文字
                ""//网站链接
        );
        return apiInfo;
    }


}
