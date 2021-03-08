package com.chentongwei.core.user.entity.vo.user;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 用户VO
 */
@Data
public class UserVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;
    /** 电子邮件 */
    private String email;
    /** 密码 */
    private String password;
    /** 头像 */
    private String avatar;
    /** 登录名(昵称) */
    private String nickname;
    /** 性别 1:男；2：女 */
    private Integer gender;
    /** 介绍 */
    private String introduce;
    /** 省份id */
    private Integer provinceId;
    /** 省份名称 */
    private String provinceName;
    /** 城市id */
    private Integer cityId;
    /** 城市名称 */
    private String cityName;
    /** 地区id */
    private Integer districtId;
    /** 地区名称 */
    private String districtName;
    /** 封面 */
    private String coverImage;
    /** 邮箱是否激活 0：未激活 1：已激活 */
    private Integer isActive;
    /** 禁用状态 0：禁用 1：正常 */
    private Integer status;
    /** 注册时间 */
    private Date createTime;
    /** token,安全验证 */
    private String token;

    public UserVO id(Long id) {
        this.id = id;
        return this;
    }
}
