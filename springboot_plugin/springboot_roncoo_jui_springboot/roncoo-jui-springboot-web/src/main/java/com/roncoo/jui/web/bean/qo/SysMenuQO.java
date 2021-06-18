package com.roncoo.jui.web.bean.qo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 菜单信息
 * </p>
 *
 * @author Wujun
 * @since 2017-11-11
 */
@Data
@Accessors(chain = true)
public class SysMenuQO implements Serializable {

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
     * 父主键
     */
    private Long parentId;
    /**
     * 菜单名称
     */
    private String menuName;
    /**
     * 菜单路径
     */
    private String menuUrl;
    /**
     * 目标名称
     */
    private String targetName;
    /**
     * 菜单图标
     */
    private String menuIcon;
    /**
     * 备注
     */
    private String remark;
}
