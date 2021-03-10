package io.springside.springtime.swagger;

import static springfox.documentation.builders.PathSelectors.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicates;

import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@ComponentScan
@Configuration
public class SpringFoxConfiguration {
	
	//TODO: manage路径从Spring里读
	@Bean
	public Docket swaggerSpringMvcPlugin() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("restful-api").select()
				.paths(Predicates.not(regex("/manage.*"))).build();
	}
}
