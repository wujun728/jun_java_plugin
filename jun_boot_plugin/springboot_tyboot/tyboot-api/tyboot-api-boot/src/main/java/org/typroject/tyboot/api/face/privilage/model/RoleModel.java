package org.typroject.tyboot.api.face.privilage.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.typroject.tyboot.core.rdbms.model.BaseModel;

import java.util.Date;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author Wujun
 * @since 2017-08-18
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RoleModel extends BaseModel {

    private static final long serialVersionUID = 78673258745325L;

    /**
     * 角色名称
     */
	private String roleName;
    /**
     * 机构编码
     */
	private String agencyCode;
    /**
     * 创建时间
     */
	private Date createTime;

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

    /**
     * 创建人
     */
	private String createUserId;
}
