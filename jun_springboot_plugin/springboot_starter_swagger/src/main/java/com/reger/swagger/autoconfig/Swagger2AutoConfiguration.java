package com.reger.swagger.autoconfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Wujun
 * @version 1.0.0
 */

public class Swagger2AutoConfiguration {

	private static final Logger log = LoggerFactory.getLogger(Swagger2AutoConfiguration.class);

	@Configuration
	@Conditional(ConditionApi.class)
	public static class swagger2Docket extends Swagger2DocketConfiguration {
		{
			log.warn( "启用了swagger文档");
		}
	}

	@RestController
	@Conditional(ConditionNotApi.class)
	public static class PreventSwaggerUi extends PreventSwaggerResourcesController {
		{
			log.warn("禁用了swagger文档html页面‘/swagger-ui.html’和其他资源的访问");
		}
	}
}
