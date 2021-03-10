package com.chentongwei.core.user.entity.io.user;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 忘记密码
 */
@Data
public class ForgetPasswordIO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    @NotBlank
    /** 电子邮箱 */
    private String email;
    @NotNull
    @NotBlank
    /** 密码 */
    private String password;
    @NotNull
    @NotBlank
    /** 电子邮箱验证码 */
    private String code;
}
