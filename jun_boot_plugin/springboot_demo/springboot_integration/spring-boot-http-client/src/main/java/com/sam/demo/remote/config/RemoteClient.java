package com.sam.demo.remote.config;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface RemoteClient {
    String value();

    String url() default "";

    String name() default "";

    RemoteType type() default RemoteType.HTTP;
}
