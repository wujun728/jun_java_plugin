package com.chentongwei.core.user.entity.io.user;

import com.chentongwei.common.entity.io.TokenIO;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 发送邮箱IO
 */
@Data
public class SendEmailIO extends TokenIO {

    /** 邮箱 */
    @NotNull
    @NotBlank
    private String email;
    /** 源邮件验证码 */
    @NotNull
    @NotBlank
    private String code;
}
