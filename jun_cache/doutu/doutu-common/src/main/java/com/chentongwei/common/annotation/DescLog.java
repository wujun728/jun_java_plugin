package com.chentongwei.common.annotation;

import java.lang.annotation.*;

/**
 * 用于记录系统操作日志的注解（具体操作描述） -- 方法注解
 *
 * @author TongWei.Chen 2017-06-01 09:57:05
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DescLog {

    /**
     * 操作描述
     *
     * @return
     */
    String value();
}
