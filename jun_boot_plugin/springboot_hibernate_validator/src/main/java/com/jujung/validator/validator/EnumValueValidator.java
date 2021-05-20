package com.jujung.validator.validator;

import com.jujung.validator.annotation.EnumValue;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumValueValidator implements ConstraintValidator<EnumValue,String> {

    private String[] enumValue;

    /**
     * 初始化时把注解中的值传过来
     * @param constraintAnnotation
     */
    @Override
    public void initialize(EnumValue constraintAnnotation) {
        this.enumValue = constraintAnnotation.value();
    }

    /**
     *
     * @param source 是要被校验的值
     * @param context
     * @return
     */
    @Override
    public boolean isValid(String source, ConstraintValidatorContext context) {
        if(source instanceof String) {
            for (String val : enumValue) {
                if(val.equals(source)) {
                    return true;
                }
            }
        }else {
            throw new IllegalArgumentException("参数类型非法");
        }
        return false;
    }
}
