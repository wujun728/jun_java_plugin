package com.mycompany.myproject.spring.initializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class MyApplicationContextInitializer implements ApplicationContextInitializer {

    private final static Logger logger = LoggerFactory.getLogger(MyApplicationContextInitializer.class);

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        logger.debug("Custom ApplicationContextInitializer : MyApplicationContextInitializer.initialize");
    }
}
