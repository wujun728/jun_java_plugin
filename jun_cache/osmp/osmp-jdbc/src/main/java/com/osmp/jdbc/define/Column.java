/*   
 * Project: OSMP
 * FileName: JdbcFinderHolder.java
 * version: V1.0
 */
package com.osmp.jdbc.define;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description:实体对象映射注解
 * 
 * @author: wangkaiping
 * @date: 2014年9月10日 下午1:48:11
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {

	/**
	 * 数据库字段
	 * 
	 * @return
	 */
	public String name() default "";

	/**
	 * 映射实体字段
	 * 
	 * @return
	 */
	public String mapField() default "";

}
