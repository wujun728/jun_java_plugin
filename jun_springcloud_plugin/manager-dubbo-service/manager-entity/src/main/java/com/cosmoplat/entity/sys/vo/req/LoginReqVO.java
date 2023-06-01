package com.cosmoplat.entity.sys.vo.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * LoginReqVO
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Data
public class LoginReqVO implements Serializable {
    @NotBlank(message = "账号不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;
//    @NotBlank(message = "验证码不能为空")
//    private String captcha;
}
