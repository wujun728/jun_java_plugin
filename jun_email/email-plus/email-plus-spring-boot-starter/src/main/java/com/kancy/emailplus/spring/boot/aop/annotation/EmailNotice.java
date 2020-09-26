package com.kancy.emailplus.spring.boot.aop.annotation;

import java.lang.annotation.*;

/**
 * EmailNotice
 *
 * @author kancy
 * @date 2020/2/22 20:16
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface EmailNotice {

    /**
     * @return
     */
    String value();

    /**
     * 业务方法出现哪些异常时触发
     * @return
     */
    Class<? extends Throwable>[] classes() default {Throwable.class};

    /**
     * 不抛出的异常
     * @return
     */
    Class<? extends Throwable>[] noThrows() default {};
}
