package org.supercall.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by kira on 16/7/24.
 */
@Component
public class GlobalConfig {
    @Value("${env.isDev}")
    private Boolean isDev;


    public Boolean getDev() {
        return isDev;
    }

    public void setDev(Boolean dev) {
        isDev = dev;
    }
}
