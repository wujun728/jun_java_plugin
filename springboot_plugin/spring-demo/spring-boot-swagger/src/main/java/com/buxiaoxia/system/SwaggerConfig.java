package com.buxiaoxia.system;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Created by xw on 2017/2/28.
 * 2017-02-28 13:11
 */
@Configuration
public class SwaggerConfig {

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.groupName("分组")
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.buxiaoxia.business.rest"))
				.paths(PathSelectors.any())
				.build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Spring boot Swagger2 Demo")
				.description("这里是个简单描述")
				.termsOfServiceUrl("http://www.baidu.com/")
				.version("1.0")
				.build();
	}
}
