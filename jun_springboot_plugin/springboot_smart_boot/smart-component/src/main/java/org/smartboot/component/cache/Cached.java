package org.smartboot.component.cache;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 缓存
 * @author Wujun
 * @version v0.1 2015年11月06日 下午1:46 Seer Exp.
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cached {

    /**
     * 缓存项前缀
     *
     * @return
     */
    PrefixEnum prefix();

    /**
     * 操作类型， 默认为读取
     *
     * @return
     */
    OperationEnum operateType() default OperationEnum.READ;

    /**
     * 静态缓存key，针对无参数的情况
     *
     * @return
     */
    String staticKey() default "";

    /** 过期时间：默认为1小时， 单位为S */
    int expire() default 60 * 60;

}
