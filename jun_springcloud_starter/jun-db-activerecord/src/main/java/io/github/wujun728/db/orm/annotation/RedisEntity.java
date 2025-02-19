package io.github.wujun728.db.orm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Redis映射实体
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisEntity {
    public final static int JSON = 0;
    public final static int BYTE = 1;
    public final static int HMAP = 2;


    String key();

    int serialType() default 0;
}
