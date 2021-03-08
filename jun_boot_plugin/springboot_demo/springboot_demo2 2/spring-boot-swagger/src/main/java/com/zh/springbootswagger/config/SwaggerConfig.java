package com.zh.springbootswagger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Wujun
 * @date 2019/5/31
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //controller包路径
                .apis(RequestHandlerSelectors.basePackage("com.zh.springbootswagger.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("xxx系统")
                .description("xxx系统 API 在线文档")
                //服务条款网址
                .termsOfServiceUrl("https://blog.csdn.net/eumenides_")
                .version("1.0")
                .contact(new Contact("是guava不是瓜娃", "https://blog.csdn.net/eumenides_", "zhanghang9202@163.com"))
                .build();
    }
}
