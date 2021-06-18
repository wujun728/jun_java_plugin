package com.ibeetl.admin.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.ibeetl.admin.core.util.enums.CoreDictType;

/**
 * 描述: 用来标注词典字段
 * 
 *
 * @author Wujun
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Dict {

    /**
     * 类型
     *
     * @return
     */
    public String type() default "";

    /**
     * 默认值
     *
     * @return
     */
    public String defaultDisplay() default "";

    /**
     * 字典文本的后缀
     *
     * @return
     */
    public String suffix() default "Text";
}
