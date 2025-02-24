package com.company.project.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;

/**
 * <p>
 * 
 * </p>
 *
 * @author project
 * @since 2020-01-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    @NotEmpty(message = "用户名不能为空！")
    private String username;

    /**
     * 密码
     */
    @NotEmpty(message = "密码不能为空！")
    private String password;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 创建人
     */
    private Integer createUser;

    /**
     * 修改时间
     */
    private Date updateDate;

    /**
     * 修改人
     */
    private Integer updateUser;

    /**
     * 删除标志0未删 1删除
     */
    @TableLogic
    private Integer delFlag;

    /**
     * 登陆返回token
     */
    @TableField(exist = false)
    private String token;

}
