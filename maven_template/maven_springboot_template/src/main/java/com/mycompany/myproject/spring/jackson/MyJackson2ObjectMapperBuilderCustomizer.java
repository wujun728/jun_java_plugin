package com.mycompany.myproject.spring.jackson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.core.Ordered;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;

@Component
public class MyJackson2ObjectMapperBuilderCustomizer implements Jackson2ObjectMapperBuilderCustomizer, Ordered {

	private final static Logger logger = LoggerFactory.getLogger(MyJackson2ObjectMapperBuilderCustomizer.class);

	@Override
	public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {

		logger.debug("custom Jackson2ObjectMapperBuilderCustomizer : MyJackson2ObjectMapperBuilderCustomizer.customize");

	}

	@Override
	public int getOrder() {
		return 1;
	}
}
