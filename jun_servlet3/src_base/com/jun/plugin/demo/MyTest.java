package com.jun.plugin.demo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD})
public @interface MyTest {
	/**
	 * 对于一人注解类来说。
	 * value属性是官方建议取的名称
	 * 且value也是默认属性
	 * 以下定义的属性，因为没有默认值 ，所以用户
	 * 在使用时必须给显式的给值
	 */
	public String value();
	/**
	 * 定义一个拥有默认值的属性
	 */
	public String name() default "NoName";
}
