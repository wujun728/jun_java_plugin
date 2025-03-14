package io.github.wujun728.db.record.annotation;

//import io.github.wujun728.db.record.mapper.RowType;
import io.github.wujun728.db.record.mapper.RowType;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 属性字段映射注解
 * JavaBean与数据库字段映射注解
 */

@Target({java.lang.annotation.ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {

    /**
     * 数据库中的字段名
     */
    String name();


    @AliasFor("name")
    public String column() default "" ;

    /**
     * 字段类型（枚举定义）
     */
    public RowType type() default RowType.STRING;

}
