package com.chentongwei.core.user.entity.io.user;

import com.chentongwei.common.entity.io.BaseGeetestIO;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 用户
 */
@Data
public class RegistUserIO extends BaseGeetestIO {

    /** 用户id */
    private Long id;
    /** 电子邮箱 */
    @NotNull
    @NotBlank
    private String email;
    /** 密码 */
    @NotNull
    @NotBlank
    private String password;
    /** 确认密码 */
    @NotNull
    @NotBlank
    private String ensurePassword;
    /** accessToken */
    private String accessToken;
    /** openId */
    private String openId;
    /** IP地址 */
    private String ip;
    /** 是否激活 0：未激活 1：已激活 */
    private Integer isActive;
}
