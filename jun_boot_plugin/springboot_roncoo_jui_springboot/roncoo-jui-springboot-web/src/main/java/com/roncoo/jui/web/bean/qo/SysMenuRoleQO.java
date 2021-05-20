package com.roncoo.jui.web.bean.qo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 菜单角色关联表
 * </p>
 *
 * @author Wujun
 * @since 2017-11-11
 */
@Data
@Accessors(chain = true)
public class SysMenuRoleQO implements Serializable {

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
     * 菜单ID
     */
    private Long menuId;
    /**
     * 角色ID
     */
    private Long roleId;
}
