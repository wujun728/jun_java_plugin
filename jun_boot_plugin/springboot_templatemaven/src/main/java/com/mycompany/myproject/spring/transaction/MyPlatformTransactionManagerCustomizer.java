package com.mycompany.myproject.spring.transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.transaction.PlatformTransactionManagerCustomizer;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

@Component
public class MyPlatformTransactionManagerCustomizer implements PlatformTransactionManagerCustomizer {

    private final static Logger logger = LoggerFactory.getLogger(MyPlatformTransactionManagerCustomizer.class);

    @Override
    public void customize(PlatformTransactionManager transactionManager) {
        logger.debug("Custom PlatformTransactionManagerCustomizer : MyPlatformTransactionManagerCustomizer.customize");
    }
}
