package com.jun.plugin.demo.annotation;

import org.springframework.context.annotation.Import;

import com.jun.plugin.demo.configuration.HelloWorldConfiguration;

import java.lang.annotation.*;

/**
 * @author MrBird
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
// @Import(HelloWorldImportSelector.class)
@Import(HelloWorldConfiguration.class)
public @interface EnableHelloWorld {
}
