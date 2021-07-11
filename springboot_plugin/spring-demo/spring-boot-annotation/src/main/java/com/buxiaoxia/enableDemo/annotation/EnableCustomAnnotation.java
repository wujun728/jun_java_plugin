package com.buxiaoxia.enableDemo.annotation;

import com.buxiaoxia.enableDemo.config.CustomAnnotation;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Created by xw on 2017/3/24.
 * 2017-03-24 17:08
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({CustomAnnotation.class})
public @interface EnableCustomAnnotation {

	String value() default "";

}
