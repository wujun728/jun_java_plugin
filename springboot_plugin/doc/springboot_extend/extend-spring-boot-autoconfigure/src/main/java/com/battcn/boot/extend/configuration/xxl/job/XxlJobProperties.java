package com.battcn.boot.extend.configuration.xxl.job;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.battcn.boot.extend.configuration.commons.ExtendBeanTemplate.XXL_JOB;

/**
 * @author Levin
 * @since 2017/12/5 0005
 */
@Data
@ConfigurationProperties(XXL_JOB)
public class XxlJobProperties {

    private Boolean enabled = false;

    private String adminAddresses;

    private String appName;

    private String ip;

    private int port;

    private String accessToken;

    private String logPath;

    private int logRetentionDays;

}
