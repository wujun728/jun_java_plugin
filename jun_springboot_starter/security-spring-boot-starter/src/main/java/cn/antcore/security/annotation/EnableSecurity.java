package cn.antcore.security.annotation;


import org.springframework.context.annotation.ComponentScan;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Login
@Documented
@Retention(RetentionPolicy.RUNTIME)
@ComponentScan(basePackages = "cn.antcore")
public @interface EnableSecurity {

}
