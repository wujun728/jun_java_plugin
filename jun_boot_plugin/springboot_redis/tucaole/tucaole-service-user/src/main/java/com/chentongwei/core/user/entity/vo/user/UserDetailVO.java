package com.chentongwei.core.user.entity.vo.user;

import com.chentongwei.common.entity.vo.AddressVO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 用户详情VO
 */
@Data
public class UserDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;
    /** 电子邮件 */
    private String email;
    /** 头像 */
    private String avatar;
    /** 登录名(昵称) */
    private String nickname;
    /** 性别 1:男；2：女 */
    private Integer gender;
    /** 介绍 */
    private String introduce;
    /** 省市区 */
    private AddressVO address;
    /** 封面 */
    private String coverImage;
    /** 邮箱是否激活 0：未激活 1：已激活 */
    private Integer isActive;
    /** 禁用状态 0：禁用 1：正常 */
    private Integer status;
    /** 注册时间 */
    private Date createTime;
}
