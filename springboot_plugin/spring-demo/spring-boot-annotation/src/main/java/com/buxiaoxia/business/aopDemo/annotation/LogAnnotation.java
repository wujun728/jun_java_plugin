package com.buxiaoxia.business.aopDemo.annotation;

import java.lang.annotation.*;

/**
 * Created by xw on 2017/3/24.
 * 2017-03-24 23:15
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {

	String value() default "";
}
