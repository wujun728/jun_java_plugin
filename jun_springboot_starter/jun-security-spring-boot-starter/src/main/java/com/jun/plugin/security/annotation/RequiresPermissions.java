package com.jun.plugin.security.annotation;

import com.jun.plugin.security.enums.Logical;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @date 2021-08-08-12:02
 * @description: 权限注解
 * @useintroduction 使用说明如下
 * 1、@RequiresPermissions("index:hello") 拥有index:hello权限
 * 2、@RequiresPermissions({"index:hello","index:world"}) 同时拥有index:hello和index:world权限
 * 3、@RequiresPermissions(value={"index:hello","index:world"},logical=Logical.OR) 拥有index:hello或index:world其一即可
 **/
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresPermissions {
    String[] value();
    Logical logical() default Logical.AND;
}
