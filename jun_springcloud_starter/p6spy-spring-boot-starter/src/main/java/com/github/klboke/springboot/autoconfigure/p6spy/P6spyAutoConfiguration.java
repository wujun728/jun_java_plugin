package com.github.klboke.springboot.autoconfigure.p6spy;

import com.p6spy.engine.spy.option.SystemProperties;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import com.p6spy.engine.spy.*;
import org.springframework.core.env.Environment;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @author: kl @kailing.pub
 * @date: 2019/11/13
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(P6SpyDriver.class)
@EnableConfigurationProperties(P6spyProperties.class)
@ConditionalOnProperty(prefix = P6spyProperties.P6SPY_CONFIG_PREFIX, name = "enabled", havingValue = "true",matchIfMissing = true)
public class P6spyAutoConfiguration implements ApplicationRunner {

    private final Environment environment;

    public P6spyAutoConfiguration(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void run(ApplicationArguments args) {
        P6spyAutoConfiguration.p6spyReload(environment);
    }

    public static void p6spyReload(Environment p6spyProperties) {
        Map<String, String> defaults = P6SpyOptions.getActiveInstance().getDefaults();
        Field[] fields = P6spyProperties.class.getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            String propertiesName = SystemProperties.P6SPY_PREFIX.concat(fieldName);
            if (p6spyProperties.containsProperty(propertiesName)) {
                String systemPropertyValue = p6spyProperties.getProperty(propertiesName, defaults.get(fieldName));
                defaults.put(fieldName, systemPropertyValue);
            }
        }
        P6SpyOptions.getActiveInstance().load(defaults);
        P6ModuleManager.getInstance().reload();
    }
}

