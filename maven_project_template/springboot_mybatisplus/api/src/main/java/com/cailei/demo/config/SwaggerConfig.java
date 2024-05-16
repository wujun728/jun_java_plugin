package com.cailei.demo.config;

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
 * @author ：蔡磊
 * @date ：2022/10/20 21:31
 * @description：配置生成接口文档
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /*swagger会帮助我们生成接口文档
     * 1：配置生成的文档信息
     * 2: 配置生成规则*/

    /*Docket封装接口文档信息*/
    @Bean
    public Docket getDocket() {

        // ApiInfo：指定生成的文档中的封面信息，文档标题、版本、作者
        ApiInfo build = new ApiInfoBuilder()
                .title("《金诺科技》后端接口说明")
                .description("此文档详细说明了金诺科技项目后端接口规范....")
                .version("v 2.0.1")
                .contact(new Contact("蔡磊", "www.jnkj.com", ""))
                .build();

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(build)//文档封面设计
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.cailei.demo.controller"))//扫包
                .paths(PathSelectors.any())//指定的包中所有的controller中的映射地址都需要生成文档
                .build();//构建docket

        return docket;
    }
}