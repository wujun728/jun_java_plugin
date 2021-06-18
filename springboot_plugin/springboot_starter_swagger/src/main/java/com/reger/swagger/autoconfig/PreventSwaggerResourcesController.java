package com.reger.swagger.autoconfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;

public class PreventSwaggerResourcesController {

	private static final Logger log = LoggerFactory.getLogger(PreventSwaggerResourcesController.class);

	@RequestMapping("/swagger-ui.html")
	public void swaggerIndex() {
		log.info("swagger api文档未开放,不允许访问");
	}

	@RequestMapping("/webjars/springfox-swagger-ui/**")
	public void swaggerResources() {
		log.info("swagger api文档未开放,不允许访问");
	}
}