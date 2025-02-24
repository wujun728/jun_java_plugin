package com.jun.plugin.security.annotation;

import com.jun.plugin.security.enums.Logical;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @date 2021-08-08-12:02
 * @description: 角色注解
 * @useintroduction 使用说明如下
 * 1、@RequiresRoles("admin") 拥有admin角色（特别说明：如果数组只传一个值的话，直接这样写就可以，无需再写大括号；多个值的话，使用大括号传值）
 * 2、@RequiresRoles({"admin","admin2"}) 同时拥有admin和admin2角色
 * 3、@RequiresRoles(value={"admin","admin2"},logical=Logical.OR) 拥有admin或admin2其一即可
 **/
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresRoles {
    String[] value();
    Logical logical() default Logical.AND;
}
