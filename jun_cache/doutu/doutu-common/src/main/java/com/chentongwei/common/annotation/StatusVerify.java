package com.chentongwei.common.annotation;

import com.chentongwei.common.annotation.validator.StatusVerifyValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 举报审核注解
 *
 * @author TongWei.Chen 2017-05-23 12:28:56
 */
@Constraint(validatedBy = StatusVerifyValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface StatusVerify {

    String message() default "只能选择审核通过或审核拒绝两种状态";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
