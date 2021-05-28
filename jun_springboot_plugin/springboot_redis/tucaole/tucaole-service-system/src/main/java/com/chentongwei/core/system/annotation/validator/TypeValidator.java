package com.chentongwei.core.system.annotation.validator;

import com.chentongwei.common.enums.constant.ValidateEnum;
import com.chentongwei.core.system.annotation.Type;
import com.chentongwei.core.system.enums.status.TypeStatusEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 类型校验器
 */
public class TypeValidator implements ConstraintValidator<Type, Integer> {

    @Override
    public void initialize(Type constraintAnnotation) {}

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        if (! Objects.equals(TypeStatusEnum.ARTICLE.value(), value) && ! Objects.equals(TypeStatusEnum.ARTICLE_COMMENT.value(), value)) {
            context.buildConstraintViolationWithTemplate(ValidateEnum.VALIDATE_ERROR.value()).addConstraintViolation();
            return false;
        }
        return true;
    }
}
