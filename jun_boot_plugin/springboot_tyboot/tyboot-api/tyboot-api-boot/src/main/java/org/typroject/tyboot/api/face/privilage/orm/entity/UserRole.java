package org.typroject.tyboot.api.face.privilage.orm.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.typroject.tyboot.core.rdbms.orm.entity.BaseEntity;

/**
 * <p>
 * 用户角色关系表
 * </p>
 *
 * @author Wujun
 * @since 2017-08-18
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("privilege_user_role")
public class UserRole extends BaseEntity {

    private static final long serialVersionUID = 456412316498L;


    /**
     * 机构编码
     */
	@TableField("AGENCY_CODE")
	private String agencyCode;
    /**
     * 机构用户表外键
     */
	@TableField("USER_ID")
	private String userId;
    /**
     * 角色表外键
     */
	@TableField("ROLE_SEQUENCE_NBR")
	private String roleSequenceNbr;
}
