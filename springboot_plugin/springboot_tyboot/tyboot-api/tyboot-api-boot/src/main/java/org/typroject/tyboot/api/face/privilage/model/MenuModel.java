package org.typroject.tyboot.api.face.privilage.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.typroject.tyboot.core.rdbms.model.BaseModel;

import java.util.Date;

/**
 * <p>
 * 菜单管理
 * </p>
 *
 * @author Wujun
 * @since 2017-08-18
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MenuModel extends BaseModel {

    private static final long serialVersionUID = 4894512315489L;


	private String agencyCode;
    /**
     * 父菜单ID
     */
	private String parentId;
    /**
     * 菜单名称
     */
	private String menuName;
    /**
     * 菜单类型（目录、菜单、页面）
     */
	private String menuType;
    /**
     * 菜单图标
     */
	private String menuIcon;
    /**
     * 菜单标题
     */
	private String menuTitle;
    /**
     * 菜单层级
     */
	private Integer menuLevel;
    /**
     * 排序
     */
	private Integer orderNum;
    /**
     * 创建人
     */
	private String createUserId;
    /**
     * 创建时间
     */
	private Date createTime;


}
