package com.hope.minio.config;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
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
 * @author Hope
 * @className: SwaggerConfiguration
 * @date 2020/7/24 17:45
 * @description: SwaggerConfiguration
 */

@Configuration
@EnableSwagger2
@EnableKnife4j
public class SwaggerConfiguration {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("MinIO 1.0")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.hope.minio.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("MinIO 对象存储服务")
                .description("文件上传系统")
                .termsOfServiceUrl("https://docs.min.io/cn")
                .contact(new Contact("Hope","https://8023.xin","18310695431@163.com"))
                .version("1.0")
                .build();
    }
}