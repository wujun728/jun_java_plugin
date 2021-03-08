package com.zh.springbootjwt.config.annotation;

import java.lang.annotation.*;

/**
 * @author Wujun
 * @date 2019/6/25
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Token {
}
