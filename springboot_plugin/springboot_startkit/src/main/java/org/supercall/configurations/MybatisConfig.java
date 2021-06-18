package org.supercall.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({"classpath:/config/spring-mybatis.xml"})
public class MybatisConfig {
}