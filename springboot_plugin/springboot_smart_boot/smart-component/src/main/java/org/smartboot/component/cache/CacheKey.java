package org.smartboot.component.cache;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 拼凑缓存Key
 *
 * @author Wujun
 * @version v0.1 2015年11月06日 下午1:46 Seer Exp.
 */
@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheKey {

    /** 缓存字段，必须具有getter方法, 为空则直接去当前参数的toString(), 当前参数为空则为"" */
    String[] fields() default {};

}
