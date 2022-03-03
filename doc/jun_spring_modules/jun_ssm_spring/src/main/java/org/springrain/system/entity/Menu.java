package org.springrain.system.entity;

import java.util.List;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springrain.frame.annotation.WhereSQL;
import org.springrain.frame.entity.BaseEntity;
/**
 * TODO 在此加入类描述
 * @copyright {@link weicms.net}
 * @author springrain<Auto generate>
 * @version  2013-07-06 16:02:58
 * @see org.springrain.system.entity.Menu
 */
@Table(name="t_menu")
public class Menu  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "Menu";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_NAME = "name";
	public static final String ALIAS_PID = "pid";
	public static final String ALIAS_DESCRIPTION = "description";
	public static final String ALIAS_PAGEURL = "pageurl";
	public static final String ALIAS_TYPE = "0.普通资源1.菜单资源";
	public static final String ALIAS_SYSTEMID = "systemId";
	public static final String ALIAS_ACTIVE = "active";
    */
	//date formats
	
	//columns START
	/**
	 * id
	 */
	private java.lang.String id;
	/**
	 * name
	 */
	private java.lang.String name;
	/**
	 * pid
	 */
	private java.lang.String pid;
	/**
	 * description
	 */
	private java.lang.String description;
	/**
	 * pageurl
	 */
	private java.lang.String pageurl;
	/**
	 * 0.普通资源1.菜单资源
	 */
	private java.lang.Integer menuType;
	/**
	 * 排序
	 */
	
	private Integer sortno;
	/**
	 * 图标样式
	 */
	private String menuIcon;
	

	/**
	 * active
	 */
	private Integer active;
	//columns END 数据库字段结束
	private String pidName;
	
	//路径编码
	private String comcode;
	
	
	
	@Transient
	public String getPidName() {
		return pidName;
	}

	public void setPidName(String pidName) {
		this.pidName = pidName;
	}

	//
	private List<Menu> leaf;
	
	//concstructor

	public Menu(){
	}

	public Menu(
		java.lang.String id
	){
		this.id = id;
	}

	//get and set
	public void setId(java.lang.String value) {
		this.id = value;
	}
	
	@Id
     @WhereSQL(sql="id=:Menu_id")
	public java.lang.String getId() {
		return this.id;
	}
	public void setName(java.lang.String value) {
		this.name = value;
	}
	
     @WhereSQL(sql="name=:Menu_name")
	public java.lang.String getName() {
		return this.name;
	}
	public void setPid(java.lang.String value) {
		this.pid = value;
	}
	
     @WhereSQL(sql="pid=:Menu_pid")
	public java.lang.String getPid() {
		return this.pid;
	}
	public void setDescription(java.lang.String value) {
		this.description = value;
	}
	
     @WhereSQL(sql="description=:Menu_description")
	public java.lang.String getDescription() {
		return this.description;
	}
	public void setPageurl(java.lang.String value) {
		this.pageurl = value;
	}
	
     @WhereSQL(sql="pageurl=:Menu_pageurl")
	public java.lang.String getPageurl() {
		return this.pageurl;
	}
	public void setMenuType(java.lang.Integer value) {
		this.menuType = value;
	}
	
     @WhereSQL(sql="menuType=:Menu_menuType")
	public java.lang.Integer getMenuType() {
		return this.menuType;
	}
     @WhereSQL(sql="sortno=:Menu_sortno")
	public Integer getSortno() {
		return sortno;
	}

	public void setSortno(Integer sortno) {
		this.sortno = sortno;
	}

	

	public void setActive(Integer value) {
		this.active = value;
	}
	
     @WhereSQL(sql="active=:Menu_active")
	public Integer getActive() {
		return this.active;
	}
     @WhereSQL(sql="menuIcon=:Menu_menuIcon")
	public String getMenuIcon() {
		return menuIcon;
	}

	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}

	@Override
    public String toString() {
		return new StringBuilder()
			.append("id[").append(getId()).append("],")
			.append("name[").append(getName()).append("],")
			.append("pid[").append(getPid()).append("],")
			.append("description[").append(getDescription()).append("],")
			.append("pageurl[").append(getPageurl()).append("],")
			.append("0.普通资源1.菜单资源[").append(getMenuType()).append("],")
			.append("active[").append(getActive()).append("],")
			.append("sortno[").append(getSortno()).append("],")
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
		if(obj instanceof Menu == false){
			return false;
		} 
		if(this == obj){
			return true;
		} 
		Menu other = (Menu)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}

	@Transient
	public List<Menu> getLeaf() {
		return leaf;
	}

	public void setLeaf(List<Menu> leaf) {
		this.leaf = leaf;
	}
	@Transient
	public String getComcode() {
		return comcode;
	}

	public void setComcode(String comcode) {
		this.comcode = comcode;
	}
}

	
