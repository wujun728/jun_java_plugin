package org.springrain.system.entity;

import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import org.springrain.frame.annotation.WhereSQL;
import org.springrain.frame.entity.BaseEntity;
/**
 * TODO 在此加入类描述
 * @copyright {@link weicms.net}
 * @author springrain<Auto generate>
 * @version  2013-07-06 16:02:59
 * @see org.springrain.system.entity.RoleMenu
 */
@Table(name="t_role_menu")
public class RoleMenu  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "RoleMenu";
	public static final String ALIAS_ID = "编号";
	public static final String ALIAS_ROLEID = "角色编号";
	public static final String ALIAS_MENUID = "菜单编号";
    */
	//date formats
	
	//columns START
	/**
	 * 编号
	 */
	private java.lang.String id;
	/**
	 * 角色编号
	 */
	private java.lang.String roleId;
	/**
	 * 菜单编号
	 */
	private java.lang.String menuId;
	//columns END 数据库字段结束
	
	//concstructor

	public RoleMenu(){
	}

	public RoleMenu(
		java.lang.String id
	){
		this.id = id;
	}

	//get and set
	public void setId(java.lang.String value) {
		this.id = value;
	}
	
	@Id
     @WhereSQL(sql="id=:RoleMenu_id")
	public java.lang.String getId() {
		return this.id;
	}
	public void setRoleId(java.lang.String value) {
		this.roleId = value;
	}
	
     @WhereSQL(sql="roleId=:RoleMenu_roleId")
	public java.lang.String getRoleId() {
		return this.roleId;
	}
	public void setMenuId(java.lang.String value) {
		this.menuId = value;
	}
	
     @WhereSQL(sql="menuId=:RoleMenu_menuId")
	public java.lang.String getMenuId() {
		return this.menuId;
	}
	
	@Override
    public String toString() {
		return new StringBuilder()
			.append("编号[").append(getId()).append("],")
			.append("角色编号[").append(getRoleId()).append("],")
			.append("菜单编号[").append(getMenuId()).append("],")
			.toString();
	}
	
	@Override
    public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	@Override
    public boolean equals(Object obj) {
		if(obj instanceof RoleMenu == false){
			return false;
		} 
		if(this == obj){
			return true;
		} 
		RoleMenu other = (RoleMenu)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

	
