package org.coody.framework.cache.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.ANNOTATION_TYPE})
@Repeatable(CacheWipes.class)
public @interface CacheWipe {
	
	String key() ;
	
	String [] fields() default "";

}
