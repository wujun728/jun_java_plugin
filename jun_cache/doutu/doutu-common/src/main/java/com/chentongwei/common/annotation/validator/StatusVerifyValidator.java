package com.chentongwei.common.annotation.validator;

import com.chentongwei.common.annotation.StatusVerify;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 图片举报审核注解的验证
 *
 * @author TongWei.Chen 2017-05-23 12:31:01
 */
public class StatusVerifyValidator implements ConstraintValidator<StatusVerify, Integer> {

    private Integer value;

    @Override
    public void initialize(StatusVerify constraintAnnotation) {
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (null == value) {
            return false;
        }
        if (! value.equals(2) && ! value.equals(3)) {
            return false;
        }
        return true;
    }
}
