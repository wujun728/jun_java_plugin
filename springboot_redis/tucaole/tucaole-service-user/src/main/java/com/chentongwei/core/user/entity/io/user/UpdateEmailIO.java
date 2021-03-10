package com.chentongwei.core.user.entity.io.user;

import com.chentongwei.common.entity.io.TokenIO;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 修改邮箱
 */
@Data
public class UpdateEmailIO extends TokenIO {

    /** 新邮箱 */
    @NotNull
    @NotBlank
    private String newEmail;
    /** 新邮箱验证码 */
    @NotNull
    @NotBlank
    private String newEmailCode;

}
