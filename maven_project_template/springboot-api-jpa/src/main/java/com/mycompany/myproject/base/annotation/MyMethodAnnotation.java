package com.mycompany.myproject.base.annotation;

import org.springframework.context.annotation.Bean;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @auther barry
 * @date 2019/2/13
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyMethodAnnotation {

    String value() default "MyMethodAnnotation";
}
