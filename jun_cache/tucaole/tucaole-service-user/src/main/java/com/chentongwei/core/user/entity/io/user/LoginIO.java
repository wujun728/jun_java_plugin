package com.chentongwei.core.user.entity.io.user;

import com.chentongwei.common.entity.io.BaseGeetestIO;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author TongWei.Chen 2017-09-21 18:46:55
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
