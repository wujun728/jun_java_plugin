package com.icloud.cache;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author Wujun
 * @create 2018/11/15.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(AutoConfiguration.class)
@Documented
@Inherited
public @interface EnableiCloudCache {
}
