package com.mall.common.annotation;


import java.lang.annotation.*;

/**
 * 参数校验注解
 * @author Wujun
 * @version 1.0
 * @create 2018/12/22 16:34
 **/
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Param {

    int errorCode() default 0; //返回码
    String name();
    String message();

}