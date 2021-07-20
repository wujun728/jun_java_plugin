package com.mycompany.myproject.spring.cache;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
public class MyCacheManagerCustomizer implements CacheManagerCustomizer {

    private final static Logger logger = LoggerFactory.getLogger(MyCacheManagerCustomizer.class);

    @Override
    public void customize(CacheManager cacheManager) {
        logger.debug("Custom CacheManagerCustomizer : MyCacheManagerCustomizer.customize");
    }
}
