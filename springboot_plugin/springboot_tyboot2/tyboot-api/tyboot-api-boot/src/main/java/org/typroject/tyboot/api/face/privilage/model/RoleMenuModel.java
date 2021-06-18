package org.typroject.tyboot.api.face.privilage.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.typroject.tyboot.core.rdbms.model.BaseModel;

import java.util.Date;

/**
 * <p>
 * 角色与菜单关系表
 * </p>
 *
 * @author Wujun
 * @since 2017-08-18
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RoleMenuModel extends BaseModel {

    private static final long serialVersionUID = 28573735432548L;

    /**
     * 机构编号
     */
	private String agencyCode;
    /**
     * 角色ID
     */
	private Long  roleSequenceNbr;
    /**
     * 菜单ID
     */
	private Long menuSequenceNbr;
	private String createUserId;
	private Date createTime;





}
