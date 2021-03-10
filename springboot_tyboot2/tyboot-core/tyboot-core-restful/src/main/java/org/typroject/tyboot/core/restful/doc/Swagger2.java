package org.typroject.tyboot.core.restful.doc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 子杨 on 2017/4/18.
 */
@Configuration
@EnableSwagger2
public class Swagger2 {
    @Bean
    public Docket createRestApi() {

        List<Parameter> pars = new ArrayList<>();


        ParameterBuilder contentType = new ParameterBuilder();
        contentType.name("Content-Type").defaultValue("application/json")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(true).build();

        ParameterBuilder product = new ParameterBuilder();
        product.name("product").defaultValue("product").description("终端类型")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(true).build();

        ParameterBuilder token = new ParameterBuilder();
        token.name("token").defaultValue("token").description("认证token")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build();

        ParameterBuilder appKey = new ParameterBuilder();
        appKey.name("appKey").defaultValue("appKey").description("appKey")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build();


        pars.add(contentType.build());    //根据每个方法名也知道当前方法在设置什么参数
        pars.add(product.build());
        pars.add(token.build());
        pars.add(appKey.build());


        return new Docket(DocumentationType.SWAGGER_2)

                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(TycloudOperation.class))
                .paths(PathSelectors.any())
                .build().globalOperationParameters(pars).apiInfo(apiInfo());
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("tyboot")
                .license("Apache-2.0")
                .description("tyboot是一个基于springboot的服务端脚手架，面向单体服务快速开发框架")
                .contact(new Contact("子杨","https://gitee.com/magintursh/tyboot","magintursh69@163.com"))
                .version("1.1.x")
                .build();
    }
}
