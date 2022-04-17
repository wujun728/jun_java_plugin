package com.dcits.tool.resume.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: yanwlb
 * @Date: 2021/10/15 14:21
 * @Description: //自定义注解，标志导入导出的excel字段名称
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelField {

    String name();
}
