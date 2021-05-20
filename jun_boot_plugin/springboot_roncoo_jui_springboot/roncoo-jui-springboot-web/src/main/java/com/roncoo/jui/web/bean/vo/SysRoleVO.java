package com.roncoo.jui.web.bean.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色信息
 * </p>
 *
 * @author Wujun
 * @since 2017-10-21
 */
@Data
@Accessors(chain = true)
public class SysRoleVO implements Serializable {

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
	 * 名称
	 */
	private String roleName;
	/**
	 * 备注
	 */
	private String remark;

	private Integer isShow;

}
