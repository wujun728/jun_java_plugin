package com.mycompany.myproject.spring.auditor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserIDAuditorAware implements AuditorAware<String> {

    private final static Logger logger = LoggerFactory.getLogger(UserIDAuditorAware.class);

    @Override
    public Optional<String> getCurrentAuditor() {

        //return Optional.empty();
        return Optional.of("Barry");
    }
}
