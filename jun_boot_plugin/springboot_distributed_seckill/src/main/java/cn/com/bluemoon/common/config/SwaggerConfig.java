package cn.com.bluemoon.common.config;

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

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
	public Docket userApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("秒杀案例").apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("cn.com.bluemoon.controller")).paths(PathSelectors.any()).build();
	}
	
	// 预览地址:swagger-ui.html
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("SpringBoot 中使用Swagger2构建文档").termsOfServiceUrl("https://blog.52itstyle.com")
				.contact(new Contact("Guoqing ", "http://www.cnblogs.com/ocean-sky/", "514471352@qq.com")).version("1.0").build();
	}
}
