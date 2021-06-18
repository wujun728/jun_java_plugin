package com.jujung.validator.annotation;

import com.jujung.validator.validator.EnumValueValidator;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Constraint: 关联解析类
 * @Target: 注解作用于的位置
 */
@Constraint(validatedBy = EnumValueValidator.class)
@Target({ElementType.FIELD,ElementType.PARAMETER,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumValue {

    String[] value() default "";

    String message() default "参数必须为指定的值";

    Class<?>[] groups() default {};

    Class<? extends javax.validation.Payload>[] payload() default {};
}
