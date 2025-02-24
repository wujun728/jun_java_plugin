package com.jun.plugin.redission.lock.annotation;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.jun.plugin.redission.lock.configuration.MQConfiguration;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target({ java.lang.annotation.ElementType.TYPE })
@Documented
@Import(MQConfiguration.class)
@Configuration
public @interface EnableMQ {

}
