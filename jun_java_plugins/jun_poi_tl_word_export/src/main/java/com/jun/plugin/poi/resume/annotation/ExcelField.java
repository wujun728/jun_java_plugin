package com.jun.plugin.poi.resume.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * NOTE: This @ExcelField annotation is duplicated in jun_poi module
 * (com.jun.plugin.poi.impexp.excel.annotation.ExcelField).
 * Consider consolidating to avoid maintaining two copies.
 *
 * @Author: yanwlb
 * @Date: 2021/10/15 14:21
 * @Description: //自定义注解，标志导入导出的excel字段名称
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelField {

    String name();
}
