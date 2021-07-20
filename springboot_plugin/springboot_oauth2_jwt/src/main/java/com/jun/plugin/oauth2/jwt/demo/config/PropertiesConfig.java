package com.jun.plugin.oauth2.jwt.demo.config;



import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.jun.plugin.oauth2.jwt.demo.properties.SecurityProperties;

/**
 * @author Wujun
 * @descriptions
 * @date 2018/11/22 0022 11:54
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class PropertiesConfig {

}
