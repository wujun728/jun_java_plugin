package org.typroject.tyboot.core.auth.face.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.typroject.tyboot.core.rdbms.model.BaseModel;

import java.util.Date;

/**
 * <p>
 * 用户登录记录
 * </p>
 *
 * @author Wujun
 * @since 2017-08-18
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LoginHistoryModel extends BaseModel {

    private static final long serialVersionUID = 52876473254488L;

    /**
     * 归属机构
     */
	private String agencyCode;
    /**
     * 登录账号
     */
	private String loginId;
    /**
     * 用户编号
     */
	private String userId;
    /**
     * 用户名
     */
	private String userName;
    /**
     * 用户类型（机构用户，公网用户,匿名用户）
     */
	private String userType;
    /**
     * 来源产品
     */
	private String actionByProduct;
    /**
     * 来源IP
     */
	private String actionByIp;
    /**
     * 过期时限(秒）
     */
	private Long sessionExpiration;
    /**
     * session状态：激活，过期
     */
	private String sessionStatus;
    /**
     * 创建时间
     */
	private Date sessionCreateTime;
	private String actionByAgent;
    /**
     * 来源设备串码
     */
	private String sourceDeviceCode;
    /**
     * 来源平台
     */
	private String sourceOs;


	private String token;

}
