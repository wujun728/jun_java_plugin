package com.chentongwei.entity.common.io;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 登录IO
 *
 * @author TongWei.Chen 2017-06-10 15:31
 **/
public class LoginIO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 登录名 */
    @NotEmpty(message = "用户名不能为空")
    @NotNull(message = "用户名不能为空")
    @NotBlank(message = "用户名不能为空白")
    private String loginName;
    /** 密码 */
    @NotEmpty(message = "密码不能为空")
    @NotNull(message = "密码不能为空")
    @NotBlank(message = "密码不能为空白")
    private String password;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
