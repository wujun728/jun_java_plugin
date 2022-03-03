package org.springrain.cms.entity;

import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springrain.frame.annotation.WhereSQL;
import org.springrain.frame.entity.BaseEntity;
/**
 * TODO 在此加入类描述
 * @copyright {@link weicms.net}
 * @author springrain<Auto generate>
 * @version  2016-11-12 10:44:58
 * @see org.springrain.cms.entity.demo.entity.CmsPropertyvalue
 */
@Table(name="cms_propertyvalue")
public class CmsPropertyvalue  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "自定义属性对应值表";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_PROPERTYID = "propertyId";
	public static final String ALIAS_PVALUE = "pvalue";
	public static final String ALIAS_SITEID = "siteId";
	public static final String ALIAS_BUSINESSID = "业务Id";
	public static final String ALIAS_SORTNO = "排序";
	public static final String ALIAS_ACTIVE = "状态 0不可用,1可用";
    */
	//date formats
	
	//columns START
	/**
	 * id
	 */
	private java.lang.String id;
	/**
	 * propertyId
	 */
	private java.lang.Integer propertyId;
	/**
	 * pvalue
	 */
	private java.lang.String pvalue;
	/**
	 * siteId
	 */
	private java.lang.String siteId;
	/**
	 * 业务Id
	 */
	private java.lang.String businessId;
	/**
	 * 排序
	 */
	private java.lang.Integer sortno;
	/**
	 * 状态 0不可用,1可用
	 */
	private java.lang.Integer active;
	//columns END 数据库字段结束
	
	//concstructor

	public CmsPropertyvalue(){
	}

	public CmsPropertyvalue(
		java.lang.String id
	){
		this.id = id;
	}

	//get and set
	public void setId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.id = value;
	}
	
	@Id
     @WhereSQL(sql="id=:CmsPropertyvalue_id")
	public java.lang.String getId() {
		return this.id;
	}
	public void setPropertyId(java.lang.Integer value) {
		this.propertyId = value;
	}
	
     @WhereSQL(sql="propertyId=:CmsPropertyvalue_propertyId")
	public java.lang.Integer getPropertyId() {
		return this.propertyId;
	}
	public void setPvalue(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.pvalue = value;
	}
	
     @WhereSQL(sql="pvalue=:CmsPropertyvalue_pvalue")
	public java.lang.String getPvalue() {
		return this.pvalue;
	}
	public void setSiteId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.siteId = value;
	}
	
     @WhereSQL(sql="siteId=:CmsPropertyvalue_siteId")
	public java.lang.String getSiteId() {
		return this.siteId;
	}
	public void setBusinessId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.businessId = value;
	}
	
     @WhereSQL(sql="businessId=:CmsPropertyvalue_businessId")
	public java.lang.String getBusinessId() {
		return this.businessId;
	}
	public void setSortno(java.lang.Integer value) {
		this.sortno = value;
	}
	
     @WhereSQL(sql="sortno=:CmsPropertyvalue_sortno")
	public java.lang.Integer getSortno() {
		return this.sortno;
	}
	public void setActive(java.lang.Integer value) {
		this.active = value;
	}
	
     @WhereSQL(sql="active=:CmsPropertyvalue_active")
	public java.lang.Integer getActive() {
		return this.active;
	}
	
	@Override
    public String toString() {
		return new StringBuilder()
			.append("id[").append(getId()).append("],")
			.append("propertyId[").append(getPropertyId()).append("],")
			.append("pvalue[").append(getPvalue()).append("],")
			.append("siteId[").append(getSiteId()).append("],")
			.append("业务Id[").append(getBusinessId()).append("],")
			.append("排序[").append(getSortno()).append("],")
			.append("状态 0不可用,1可用[").append(getActive()).append("],")
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
		if(obj instanceof CmsPropertyvalue == false){
			return false;
		} 
		if(this == obj){
			return true;
		} 
		CmsPropertyvalue other = (CmsPropertyvalue)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

	
