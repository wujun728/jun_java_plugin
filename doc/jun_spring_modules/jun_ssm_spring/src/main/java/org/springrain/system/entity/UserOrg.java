package org.springrain.system.entity;

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
 * @version  2013-07-06 16:03:00
 * @see org.springrain.system.entity.UserOrg
 */
@Table(name="t_user_org")
public class UserOrg  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "UserOrg";
	public static final String ALIAS_ID = "编号";
	public static final String ALIAS_USERID = "用户编号";
	public static final String ALIAS_ORGID = "机构编号";
	public static final String ALIAS_MANAGER = "0.不是1.是";
    */
	//date formats
	
	//columns START
	/**
	 * 编号
	 */
	private java.lang.String id;
	/**
	 * 用户编号
	 */
	private java.lang.String userId;
	/**
	 * 机构编号
	 */
	private java.lang.String orgId;
	/**
	 * 是否主管(0非主管1主管2代主管)
	 * 
	 */
	private Integer managerType;
	private String orgName;
	public Integer getHasleaf() {
		return hasleaf;
	}

	public void setHasleaf(Integer hasleaf) {
		this.hasleaf = hasleaf;
	}
	/**
	 * 是否包含子部门  和是否是主管合用
	 * 0不包含  1包含
	 */
	private Integer hasleaf;
	/**
	 * 人员部门关系，是正常还是虚拟
	 * 0正常  1虚拟
	 */
	//columns END 数据库字段结束
	
	//concstructor

	public UserOrg(){
	}

	public UserOrg(
		java.lang.String id
	){
		this.id = id;
	}

	//get and set
	public void setId(java.lang.String value) {
		this.id = value;
	}
	
	@Id
     @WhereSQL(sql="id=:UserOrg_id")
	public java.lang.String getId() {
		return this.id;
	}
	public void setUserId(java.lang.String value) {
		this.userId = value;
	}
	
     @WhereSQL(sql="userId=:UserOrg_userId")
	public java.lang.String getUserId() {
		return this.userId;
	}
	public void setOrgId(java.lang.String value) {
		this.orgId = value;
	}
	
     @WhereSQL(sql="orgId=:UserOrg_orgId")
	public java.lang.String getOrgId() {
		return this.orgId;
	}
	
	
	@Override
    public String toString() {
		return new StringBuilder()
			.append("编号[").append(getId()).append("],")
			.append("用户编号[").append(getUserId()).append("],")
			.append("机构编号[").append(getOrgId()).append("],")
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
		if(obj instanceof UserOrg == false){
			return false;
		} 
		if(this == obj){
			return true;
		} 
		UserOrg other = (UserOrg)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
	@Transient
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Integer getManagerType() {
		return managerType;
	}

	public void setManagerType(Integer managerType) {
		this.managerType = managerType;
	}
}

	
