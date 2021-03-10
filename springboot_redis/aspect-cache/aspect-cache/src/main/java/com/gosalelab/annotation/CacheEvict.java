package com.gosalelab.annotation;

import java.lang.annotation.*;

/**
 * evict cache annotation
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CacheEvict {
    /**
     * prefix of cache key
     */
    String pre() default "";

    /**
     * cache key
     */
    String key() default "";

    /**
     * cache description
     */
    String desc() default "";

}
