package org.springsession.demo.javaconfig.init;

import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;
import org.springsession.demo.javaconfig.config.Config;

public class Initializer extends AbstractHttpSessionApplicationInitializer {

    public Initializer() {
        super(Config.class);
    }
}