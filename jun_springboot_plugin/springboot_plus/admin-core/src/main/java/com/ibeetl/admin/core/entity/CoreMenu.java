package com.ibeetl.admin.core.entity;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.SeqID;
import org.beetl.sql.core.annotatoin.UpdateIgnore;

import com.ibeetl.admin.core.annotation.Dict;
import com.ibeetl.admin.core.util.ValidateConfig;
import com.ibeetl.admin.core.util.enums.CoreDictType;

/**
 * 系统菜单
 */
public class CoreMenu extends BaseEntity {

    public static final String TYPE_SYSTEM = "MENU_S";
    public static final String TYPE_NAV = "MENU_N";
    public static final String TYPE_MENUITEM = "MENU_M";

	@NotNull(message = "ID不能为空", groups = ValidateConfig.UPDATE.class)
	@SeqID(name = ORACLE_CORE_SEQ_NAME)
	@AutoID
    protected Long id;

    //创建时间
	@UpdateIgnore
    protected Date createTime;

    //菜单代码
    @NotBlank(message = "菜单代码不能为空", groups = ValidateConfig.ADD.class)
    private String code;

    //功能id
    private Long functionId;

    //类型  /*1 系统 2 导航 3 菜单项(与功能点有关)*/
    @NotNull(message = "菜单类型不能为空")
    @Dict(type = CoreDictType.MENU_TYPE)
    private String type;

    //菜单名称
    @NotBlank(message = "菜单名称不能为空")
    private String name;

    //上层菜单id
    @NotNull(message = "上层菜单不能为空")
    private Long parentMenuId;

    //排序
    @NotNull(message = "排序不能为空")
    private Integer seq;

    //图标
    private String icon;

    public CoreMenu() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getFunctionId() {
        return functionId;
    }

    public void setFunctionId(Long functionId) {
        this.functionId = functionId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentMenuId() {
        return parentMenuId;
    }

    public void setParentMenuId(Long parentMenuId) {
        this.parentMenuId = parentMenuId;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
    
    
}
