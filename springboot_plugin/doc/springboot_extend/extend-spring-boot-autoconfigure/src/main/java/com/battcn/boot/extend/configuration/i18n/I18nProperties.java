package com.battcn.boot.extend.configuration.i18n;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.battcn.boot.extend.configuration.commons.ExtendBeanTemplate.I18N;

/**
 * 国际化资源配置属性
 *
 * @author Levin
 * @since 2017/12/5 0005
 */
@Data
@ConfigurationProperties(prefix = I18N)
public class I18nProperties {

    private boolean enabled = true;
    /**
     * 默认 UTF-8
     */
    private String defaultEncoding;
    private String[] baseNames;

}
