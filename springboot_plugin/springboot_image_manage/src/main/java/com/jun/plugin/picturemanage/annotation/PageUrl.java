

package com.jun.plugin.picturemanage.annotation;

import java.lang.annotation.*;

/**
 * 页面路径映射注解
 *
 * @author EddyChen
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PageUrl {

	String value() default "";
}
