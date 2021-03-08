package org.typroject.tyboot.api.face.privilage.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.typroject.tyboot.core.rdbms.model.BaseModel;

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
public class UserRoleModel extends BaseModel {

    private static final long serialVersionUID = 5587398945434L;


    /**
     * 机构编码
     */
	private String agencyCode;
    /**
     * 机构用户表外键
     */
	private String userId;
    /**
     * 角色表外键
     */
	private String roleSequenceNbr;
}
