package org.typroject.tyboot.core.auth.face.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.typroject.tyboot.core.rdbms.model.BaseModel;

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
public class LoginInfoModel extends BaseModel {

    private static final long serialVersionUID = 48465411351535L;


    /**
     * 登录ID
     */
	private String loginId;
    /**
     * 系统用户ID
     */
	private String userId;
	private String password;
    /**
     * 密码随机盐
     */
	private String salt;
	private String userType;
	private String agencyCode;

	private String idType;
    /**
     * 数据锁定状态: N :非锁定 / Y: 锁定
     */
	private String lockStatus;
    /**
     * 数据锁定时间
     */
	private Date lockDate;
    /**
     * 锁定人ID外键
     */
	private String lockUserId;


}
