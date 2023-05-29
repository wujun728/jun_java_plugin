package com.jun.plugin.util4j.hotSwap.annationScriptFactory.annations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 脚本标记注解
 * @author juebanlin
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AnnationScript{
	
	/**
	 * int类型的映射,为0不映射
	 * @return
	 */
	int id() default 0;
	
	/**
	 * string类型的映射,null或者为空不映射
	 * @return
	 */
	String name() default "";
}
