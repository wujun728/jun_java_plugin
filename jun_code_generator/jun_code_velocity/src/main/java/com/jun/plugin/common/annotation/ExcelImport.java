package com.jun.plugin.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * excel导入注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelImport {
    /**
     * 导入excel的第几列，从0开始
     * @return
     */
    public int colNum();

    /**
     * 导入excel的列头名称
     * @return
     */
    public String colName();
    
    /**
     * 提示信息
     */
    public abstract String prompt() default "";
}
