package com.roncoo.jui.web.bean.qo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 后台用户信息
 * </p>
 *
 * @author Wujun
 * @since 2017-11-11
 */
@Data
@Accessors(chain = true)
public class SysUserQO implements Serializable {

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
     * 用户手机
     */
    private String userPhone;
    /**
     * 用户邮箱
     */
    private String userEmail;
    /**
     * 真实姓名
     */
    private String userRealname;
    /**
     * 用户昵称
     */
    private String userNickname;
    /**
     * 性别
     */
    private String userSex;
    /**
     * 密码盐
     */
    private String salt;
    /**
     * 用户密码
     */
    private String pwd;
    /**
     * 备注
     */
    private String remark;
}
