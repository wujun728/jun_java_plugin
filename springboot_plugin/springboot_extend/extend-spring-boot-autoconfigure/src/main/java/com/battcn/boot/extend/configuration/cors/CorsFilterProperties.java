package com.battcn.boot.extend.configuration.cors;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotNull;

import static com.battcn.boot.extend.configuration.commons.ExtendBeanTemplate.CORS;

/**
 * Core 跨域相关配置
 *
 * @author Levin
 * @since 2017/12/5 0005
 */
@Data
@ConfigurationProperties(CORS)
public class CorsFilterProperties {

    private Boolean enabled;
    private String path;
    private String origin;
    private String allowedHeader;
    private String method;
    private String exposedHeader;

    @NotNull
    private Boolean allowCredentials;
    @NotNull
    private Long maxAge;

}
