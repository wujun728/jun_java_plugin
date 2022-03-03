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
 * @see org.springrain.system.entity.Org
 */
@Table(name="t_org")
public class Org  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "Org";
	public static final String ALIAS_ID = "编号";
	public static final String ALIAS_NAME = "名称";
	public static final String ALIAS_COMCODE = "代码";
	public static final String ALIAS_PID = "上级部门ID";
	public static final String ALIAS_SYSID = "子系统ID";
	public static final String ALIAS_TYPE = "0,组织机构 1.部门";
	public static final String ALIAS_LEAF = "叶子节点(0:树枝节点;1:叶子节点)";
	public static final String ALIAS_SORTNO = "排序号";
	public static final String ALIAS_DESCRIPTION = "描述";
	public static final String ALIAS_ACTIVE = "0.失效 1.有效";
    */
	//date formats
	
	//columns START
	/**
	 * 编号
	 */
	private java.lang.String id;
	/**
	 * 名称
	 */
	private java.lang.String name;
	/**
	 * 代码
	 */
	private java.lang.String comcode;
	/**
	 * 上级部门ID
	 */
	private java.lang.String pid;
	/**
	 * 0,组织机构 1.部门,2.虚拟部门
	 */
	private java.lang.Integer orgType;
	/**
	 * 叶子节点(0:树枝节点;1:叶子节点)
	 */
	private java.lang.Integer leaf;
	/**
	 * 排序号
	 */
	private java.lang.Integer sortno;
	/**
	 * 描述
	 */
	private java.lang.String description;
	/**
	 * 0.失效 1.有效
	 */
	private java.lang.Integer active;
	
	private List<Org> leafOrg;
	
	//concstructor

	public Org(){
	}

	public Org(
		java.lang.String id
	){
		this.id = id;
	}

	//get and set
	public void setId(java.lang.String value) {
		this.id = value;
	}
	
	@Id
     @WhereSQL(sql="id=:Org_id")
	public java.lang.String getId() {
		return this.id;
	}
	public void setName(java.lang.String value) {
		this.name = value;
	}
	
     @WhereSQL(sql="name=:Org_name")
	public java.lang.String getName() {
		return this.name;
	}
	public void setComcode(java.lang.String value) {
		this.comcode = value;
	}
	
     @WhereSQL(sql="comcode=:Org_comcode")
	public java.lang.String getComcode() {
		return this.comcode;
	}
	public void setPid(java.lang.String value) {
		this.pid = value;
	}
	
     @WhereSQL(sql="pid=:Org_pid")
	public java.lang.String getPid() {
		return this.pid;
	}
	public void setOrgType(java.lang.Integer value) {
		this.orgType = value;
	}
	
     @WhereSQL(sql="orgType=:Org_orgType")
	public java.lang.Integer getOrgType() {
		return this.orgType;
	}
	public void setLeaf(java.lang.Integer value) {
		this.leaf = value;
	}
	
     @WhereSQL(sql="leaf=:Org_leaf")
	public java.lang.Integer getLeaf() {
		return this.leaf;
	}
	public void setSortno(java.lang.Integer value) {
		this.sortno = value;
	}
	
     @WhereSQL(sql="sortno=:Org_sortno")
	public java.lang.Integer getSortno() {
		return this.sortno;
	}
	public void setDescription(java.lang.String value) {
		this.description = value;
	}
	
     @WhereSQL(sql="description=:Org_description")
	public java.lang.String getDescription() {
		return this.description;
	}
	public void setActive(Integer value) {
		this.active = value;
	}
	
     @WhereSQL(sql="active=:Org_active")
	public Integer getActive() {
		return this.active;
	}

	@Override
    public String toString() {
		return new StringBuilder()
			.append("编号[").append(getId()).append("],")
			.append("名称[").append(getName()).append("],")
			.append("代码[").append(getComcode()).append("],")
			.append("上级部门ID[").append(getPid()).append("],")
			.append("0,组织机构 1.部门,2虚拟部门[").append(getOrgType()).append("],")
			.append("叶子节点(0:树枝节点;1:叶子节点)[").append(getLeaf()).append("],")
			.append("排序号[").append(getSortno()).append("],")
			.append("描述[").append(getDescription()).append("],")
			.append("0.失效 1.有效[").append(getActive()).append("],")
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
		if(obj instanceof Org == false){
			return false;
		} 
		if(this == obj){
			return true;
		} 
		Org other = (Org)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}

	@Transient
	public List<Org> getLeafOrg() {
		return leafOrg;
	}

	public void setLeafOrg(List<Org> leafOrg) {
		this.leafOrg = leafOrg;
	}
}

	
