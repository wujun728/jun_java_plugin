package com.chentongwei.core.user.entity.io.user;

import com.chentongwei.common.entity.io.BaseGeetestIO;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 登录IO
 */
@Data
public class LoginIO extends BaseGeetestIO {

    /** 登录邮箱 */
    @NotNull
    @NotBlank
    private String email;
    /** 登录密码 */
    @NotNull
    @NotBlank
    private String password;
}
