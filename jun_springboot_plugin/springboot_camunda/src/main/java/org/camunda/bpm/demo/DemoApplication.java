package org.camunda.bpm.demo;

import java.util.Arrays;
import java.util.HashMap;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.camunda.bpm.spring.boot.starter.event.PostDeployEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableProcessApplication
@ComponentScan({"org.camunda.bpm"})
@EnableAutoConfiguration
@EnableFeignClients(basePackages="org.camunda.bpm.demo")
@EnableEurekaClient
@EnableSwagger2
public class DemoApplication {

	@Autowired
	private RuntimeService runtimeService;

	public static void main(String... args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@EventListener
	private void processPostDeploy(PostDeployEvent event) {
		runtimeService.startProcessInstanceByKey("loanApproval");
		
		HashMap<String, Object> variables = new HashMap<String, Object>();
		variables.put("assigneeList030", Arrays.asList("kermit", "demo"));
		variables.put("assigneeList040", Arrays.asList("kermit", "demo"));
		variables.put("starter","demo");
		variables.put("amount","980");
		runtimeService.startProcessInstanceByKey("Process_Demo1",variables);
	}
	
	//关闭spring boot jackson的FAIL_ON_EMPTY_BEANS
	@Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }
	
	//Camunda CORS Filter in Spring Boot Application
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowCredentials(true);
		configuration.addAllowedOrigin("*");
		configuration.addAllowedHeader("*");
		configuration.addAllowedMethod("*");
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}