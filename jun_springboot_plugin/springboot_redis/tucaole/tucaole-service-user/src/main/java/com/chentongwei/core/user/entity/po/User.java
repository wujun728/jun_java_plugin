package com.chentongwei.core.user.entity.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * @author Wujun
 * @Project tucaole
 * @Description: 用户表
 */
@Data
public class User implements Serializable {
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
    /** accessToken */
    private String accessToken;
    /** openId */
    private String openId;
    /** IP地址 */
    private String ip;
    /** 是否是管理员 0：普通用户 1：管理员 */
    private Integer isAdmin;
    /** 邮箱是否激活 0：未激活 1：已激活 */
    private Integer isActive;
    /** 禁用状态 0：禁用 1：正常 */
    private Integer status;
    /** 注册时间 */
    private Date createTime;
}