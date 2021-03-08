package com.chentongwei.core.user.entity.vo.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 登录后返回的信息
 */
@Data
public class LoginVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;
    /** 登录名(昵称) */
    private String nickname;
    /** 邮箱是否激活 0：未激活 1：已激活 */
    private Integer isActive;
    /** 禁用状态 0：禁用 1：正常 */
    private Integer status;
    /** token,安全验证 */
    private String token;
}
