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
public class AgencyMenuModel extends BaseModel {

    private static final long serialVersionUID = 144894231L;


	private String agencyCode;
    /**
     * 菜单ID
     */
	private String menuSequenceNbr;
	private String createUserId;
	private Date createTime;


}
