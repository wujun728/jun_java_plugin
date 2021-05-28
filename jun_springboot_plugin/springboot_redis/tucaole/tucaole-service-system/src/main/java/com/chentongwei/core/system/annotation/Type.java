package com.chentongwei.core.system.annotation;

import com.chentongwei.core.system.annotation.validator.TypeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 类型：1：吐槽的文章；2：吐槽文章的评论；
 */
@Constraint(validatedBy = TypeValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Type {

    String message() default "{com.chentongwei.core.system.annotation.Type.message}";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
