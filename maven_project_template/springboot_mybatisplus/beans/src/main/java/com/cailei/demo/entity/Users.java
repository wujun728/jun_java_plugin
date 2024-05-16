package com.cailei.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户 
 * @TableName users
 */
@Data
@TableName(value ="users")
@ApiModel(value = "User对象", description = "用户信息")
public class Users implements Serializable {
    /**
     * 主键id 用户id
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    @ApiModelProperty(dataType = "int", value = "用户ID",hidden = true)
    private Integer userId;

    /**
     * 用户名 用户名
     */
    @TableField(value = "username")
    @ApiModelProperty(dataType = "String", value = "用户名")
    private String username;

    /**
     * 密码 密码
     */
    @TableField(value = "password")
    @ApiModelProperty(dataType = "String", value = "密码")
    private String password;

    /**
     * 昵称 昵称
     */
    @TableField(value = "nickname")
    @ApiModelProperty(dataType = "String", value = "昵称")
    private String nickname;

    /**
     * 真实姓名 真实姓名
     */
    @TableField(value = "realname")
    @ApiModelProperty(dataType = "String", value = "真实姓名")
    private String realname;

    /**
     * 头像 头像
     */
    @TableField(value = "user_img")
    @ApiModelProperty(dataType = "String", value = "头像")
    private String userImg;

    /**
     * 手机号 手机号
     */
    @TableField(value = "user_mobile")
    @ApiModelProperty(dataType = "String", value = "手机号")
    private String userMobile;

    /**
     * 邮箱地址 邮箱地址
     */
    @TableField(value = "user_email")
    @ApiModelProperty(dataType = "String", value = "邮箱地址")
    private String userEmail;

    /**
     * 性别 M(男) or F(女)
     */
    @TableField(value = "user_sex")
    @ApiModelProperty(dataType = "String", value = "性别 M(男) or F(女)")
    private String userSex;

    /**
     * 生日 生日
     */
    @TableField(value = "user_birth")
    @ApiModelProperty(dataType = "Date", value = "生日")
    private Date userBirth;

    /**
     * 注册时间 创建时间
     */
    @TableField(value = "user_regtime")
    @ApiModelProperty(dataType = "Date", value = "创建时间")
    private Date userRegtime;

    /**
     * 更新时间 更新时间
     */
    @TableField(value = "user_modtime")
    @ApiModelProperty(dataType = "Date", value = "更新时间")
    private Date userModtime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}