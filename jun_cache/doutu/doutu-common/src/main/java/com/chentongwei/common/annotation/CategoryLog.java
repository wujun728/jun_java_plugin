package com.chentongwei.common.annotation;

import java.lang.annotation.*;

/**
 * 用于记录系统操作日志的注解（菜单名） -- 类注解
 *
 * @author TongWei.Chen 2017-06-01 09:53:36
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

    /**
     * 子菜单名
     *
     * @return
     */
    String subMenu();
}