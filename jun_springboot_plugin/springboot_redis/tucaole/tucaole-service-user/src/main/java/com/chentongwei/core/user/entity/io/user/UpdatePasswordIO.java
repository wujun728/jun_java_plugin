package com.chentongwei.core.user.entity.io.user;

import com.chentongwei.common.entity.io.TokenIO;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 忘记密码
 */
@Data
public class UpdatePasswordIO extends TokenIO {

    @NotNull
    @NotBlank
    /** 原密码 */
    private String oldPassword;
    /** 新密码 */
    @NotNull
    @NotBlank
    private String newPassword;
    /** 确认密码 */
    @NotNull
    @NotBlank
    private String ensurePassword;
}
