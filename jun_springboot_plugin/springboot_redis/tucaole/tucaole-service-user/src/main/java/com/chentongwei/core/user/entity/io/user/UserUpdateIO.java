package com.chentongwei.core.user.entity.io.user;

import com.chentongwei.common.entity.io.AddressIO;
import com.chentongwei.common.entity.io.TokenIO;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 更新个人信息IO
 */
@Data
public class UserUpdateIO extends TokenIO {

    /** 头像 */
    private String avatar;
    /** 登录名(昵称) */
    @NotNull
    @NotBlank
    private String nickname;
    /** 性别 1:男；2：女 */
    @NotNull
    private Integer gender;
    /** 介绍 */
    private String introduce;
    /** 省市区 */
    private AddressIO address;
    /** 封面 */
    private String coverImage;
}
