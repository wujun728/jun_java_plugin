package com.lhb.lhbauth.jwt.demo.config;



import com.lhb.lhbauth.jwt.demo.properties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Wujun
 * @descriptions
 * @date 2018/11/22 0022 11:54
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class PropertiesConfig {

}
