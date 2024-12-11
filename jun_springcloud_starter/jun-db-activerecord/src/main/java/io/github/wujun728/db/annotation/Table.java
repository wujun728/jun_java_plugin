package com.gysoft.jdbc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 实体映射表自定义注解
 * @author 彭佳佳
 */
@Documented
@Target(TYPE)
@Retention(RUNTIME)
public @interface Table {
	/**
	 * 表名称
	 * @return String
	 */
	String name() default "";

	/**
	 * 主键字段
	 * @return String
	 */
	String pk() default "id";
}
