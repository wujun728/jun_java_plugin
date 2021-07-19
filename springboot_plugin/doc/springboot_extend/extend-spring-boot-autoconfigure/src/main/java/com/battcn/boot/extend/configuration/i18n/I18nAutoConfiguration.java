package com.battcn.boot.extend.configuration.i18n;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.Validator;

import static com.battcn.boot.extend.configuration.commons.ExtendBeanTemplate.*;
import static org.apache.commons.lang3.StringUtils.defaultString;

/**
 * @author Levin
 * @since 2019-07-30
 */
@Configuration
@EnableConfigurationProperties(value = {I18nProperties.class})
@ConditionalOnProperty(prefix = I18N, name = ENABLED, havingValue = TRUE, matchIfMissing = true)
public class I18nAutoConfiguration {

    private final I18nProperties properties;
    private static final String DEFAULT_CHARSET = "UTF-8";

    @Autowired
    public I18nAutoConfiguration(I18nProperties properties) {
        this.properties = properties;
    }

    private ResourceBundleMessageSource getMessageSource() {
        ResourceBundleMessageSource bundleMessageSource = new ResourceBundleMessageSource();
        bundleMessageSource.setDefaultEncoding(defaultString(properties.getDefaultEncoding(), DEFAULT_CHARSET));
        bundleMessageSource.setBasenames(properties.getBaseNames());
        return bundleMessageSource;
    }

    /**
     * 注入 Validator 验证 Bean
     *
     * @return Validator
     */
    @Bean
    public Validator getValidator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource(getMessageSource());
        return validator;
    }
}
