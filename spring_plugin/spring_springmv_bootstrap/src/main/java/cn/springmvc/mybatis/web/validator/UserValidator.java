package cn.springmvc.mybatis.web.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import cn.springmvc.mybatis.web.command.UserCommand;

/**
 * @author Vincent.wang
 *
 */
public class UserValidator implements Validator {

    public static final String LOGIN = "login";
    public static final String UPDATE_PASSWORD = "updatepassword";

    private String type;

    public UserValidator(String type) {
        this.type = type;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserCommand.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserCommand command = (UserCommand) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "field.required");
        if (StringUtils.equals(type, LOGIN)) {
            // 登录校验
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "field.required");

        } else if (StringUtils.equals(type, UPDATE_PASSWORD)) {
            // 修改密码校验
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newPassword", "field.required");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordAgain", "field.required");
            if (!errors.hasFieldErrors("newPassword") && !errors.hasFieldErrors("passwordAgain")) {
                if (!command.getNewPassword().equals(command.getPasswordAgain())) {
                    errors.rejectValue("passwordAgain", "user.passwordAgain.error");
                }
            }

        }
    }

}
