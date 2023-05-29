package org.camunda.bpm.demo.conf;


import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class DemoWebMvcConfig implements WebMvcConfigurer {
	private static final Logger logger = LoggerFactory.getLogger(DemoWebMvcConfig.class);
	
	private static List<String> AUTH_WHITELIST=Arrays.asList(
			// -- swagger ui
			"/v2/api-docs", 
			"/swagger-resources", 
			"/swagger-resources/**", 
			"/configuration/ui",
			"/configuration/security", 
			"/swagger-ui.html", 
			"/webjars/**",
			"/error",
			"/actuator/health",
			"/actuator/info",
			"/actuator",
			"/webjars/springfox-swagger-ui",
			".css",
			".js"
	);
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new HandlerInterceptor() {
			@Override
			public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
					throws Exception {

				String requestURI = request.getRequestURI();

				logger.info(">>>>>>>>>>>>>请求路径>>>>>>>>>>>>>>>>" + requestURI);
				for (String singleAuth : AUTH_WHITELIST) {
					if (requestURI.contains(singleAuth)) {
						return true;
					}
				}

				return true;
			}

			@Override
			public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
					@Nullable Exception ex) throws Exception {

			}
		});
	}
}
