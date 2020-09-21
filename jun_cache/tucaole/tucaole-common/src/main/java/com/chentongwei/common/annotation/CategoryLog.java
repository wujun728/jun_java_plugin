package com.chentongwei.common.annotation;

import java.lang.annotation.*;

/**
 * @author TongWei.Chen 2017-9-21 19:31:28
 * @Project tucaole
 * @Description: 用于记录系统操作日志的注解（菜单名） -- 类注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CategoryLog {

    /**
     * 一级菜单名
     *
     * @return
     */
    String menu();
}