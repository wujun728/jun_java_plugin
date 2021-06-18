package org.typroject.tyboot.core.auth.face.orm.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.typroject.tyboot.core.rdbms.orm.entity.BaseEntity;

import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author Wujun
 * @since 2017-08-18
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("auth_login_info")
public class LoginInfo extends BaseEntity {

    private static final long serialVersionUID = 1854894151651564L;


    /**
     * 登录ID
     */
	@TableField("LOGIN_ID")
	private String loginId;
    /**
     * 系统用户ID
     */
	@TableField("USER_ID")
	private String userId;
	@TableField("PASSWORD")
	private String password;
    /**
     * 密码随机盐
     */
	@TableField("SALT")
	private String salt;
	@TableField("USER_TYPE")
	private String userType;
	@TableField("AGENCY_CODE")
	private String agencyCode;

	@TableField("ID_TYPE")
	private String idType;

    /**
     * 数据锁定状态: N :非锁定 / Y: 锁定
     */
	@TableField("LOCK_STATUS")
	private String lockStatus;
    /**
     * 数据锁定时间
     */
	@TableField("LOCK_DATE")
	private Date lockDate;
    /**
     * 锁定人ID外键
     */
	@TableField("LOCK_USER_ID")
	private String lockUserId;

}