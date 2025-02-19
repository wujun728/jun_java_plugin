package io.github.wujun728.db.record.annotation;

//import com.ruoyi.framework.aspectj.lang.enums.RowType;

import io.github.wujun728.db.record.mapper.RowType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * JavaBean与数据库字段映射注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MapRow {

    /**
     * 数据库中的字段名
     */
    public String column() default "";

    /**
     * 字段类型（枚举定义）
     */
    public RowType type() default RowType.STRING;

}