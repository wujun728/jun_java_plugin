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
 * @version  2017-02-15 15:02:34
 * @see org.springrain.cms.base.entity.CmsPraise
 */
@Table(name="cms_praise")
public class CmsPraise  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "点赞表";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_BUSINESSID = "业务id";
	public static final String ALIAS_USERID = "点赞人";
    */
	//date formats
	
	//columns START
	/**
	 * id
	 */
	private java.lang.String id;
	/**
	 * 业务id
	 */
	private java.lang.String businessId;
	/**
	 * 点赞人
	 */
	private java.lang.String userId;
	
	
	private String siteId;
	
	//columns END 数据库字段结束
	
	//concstructor

	public CmsPraise(){
	}

	public CmsPraise(
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
     @WhereSQL(sql="id=:CmsPraise_id")
	public java.lang.String getId() {
		return this.id;
	}
	public void setBusinessId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.businessId = value;
	}
	
     @WhereSQL(sql="businessId=:CmsPraise_businessId")
	public java.lang.String getBusinessId() {
		return this.businessId;
	}
	public void setUserId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.userId = value;
	}
	
     @WhereSQL(sql="userId=:CmsPraise_userId")
	public java.lang.String getUserId() {
		return this.userId;
	}
	
	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	@Override
    public String toString() {
		return new StringBuilder()
			.append("id[").append(getId()).append("],")
			.append("业务id[").append(getBusinessId()).append("],")
			.append("点赞人[").append(getUserId()).append("],")
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
		if(obj instanceof CmsPraise == false){
			return false;
		} 
		if(this == obj){
			return true;
		} 
		CmsPraise other = (CmsPraise)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

	
