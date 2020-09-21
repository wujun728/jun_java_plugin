package org.coody.framework.rcc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.coody.framework.core.annotation.AutoBuild;

/**
 * 服务者
 * @author Coody
 *
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME) 
@AutoBuild
public @interface RccImplement {

	/**
	 * ip地址
	 * @return
	 */
	String host() default "";
	/**
	 * 端口
	 * @return
	 */
	String port() default "";
	/**
	 * 服务名
	 * @return
	 */
	String value() default "";
	
}
