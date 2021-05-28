package com.roncoo.jui.web.bean.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 后台用户信息
 * </p>
 *
 * @author Wujun
 * @since 2017-10-21
 */
@Data
@Accessors(chain = true)
public class SysUserVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;
    /**
     * 创建时间
     */
    private Date gmtCreate;
    /**
     * 修改时间
     */
    private Date gmtModified;
    /**
     * 状态
     */
    private String statusId;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 用户状态
     */
    private String userStatus;
    /**
     * 手机
     */
    private String userPhone;
    /**
     * 邮箱
     */
    private String userEmail;
    /**
     * 用户姓名
     */
    private String userName;
    /**
     * 性别
     */
    private String userSex;
    /**
     * 真实姓名
     */
    private String userRealname;
    /**
     * 昵称
     */
    private String userNickname;
    /**
     * 密码盐
     */
    private String salt;
    /**
     * 用户密码
     */
    private String pwd;
    /**
     * 组织全路径ID
     */
    private String orgMergerId;
    /**
     * 组织全路径名称
     */
    private String orgMergerName;
    /**
     * 头像
     */
    private String headImage;
    /**
     * 备注
     */
    private String remark;



}
