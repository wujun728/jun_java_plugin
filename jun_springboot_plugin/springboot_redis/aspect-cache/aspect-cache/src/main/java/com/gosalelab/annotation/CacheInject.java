package com.gosalelab.annotation;

import com.gosalelab.constants.CacheOpType;

import java.lang.annotation.*;

/**
 * use cache annotation
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CacheInject {

    /**
     * cache key
     */
    String key() default "";

    /**
     * expire time，unit：second
     */
    int expire() default 0;

    /**
     * prefix of cache
     */
    String pre() default "";

    /**
     * key description
     */
    String desc() default "";

    /**
     * cache operation type
     * @return
     */
    CacheOpType opType() default CacheOpType.READ_WRITE;

}
