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
 * @version  2016-11-12 10:44:57
 * @see org.springrain.cms.entity.demo.entity.CmsFriendSite
 */
@Table(name="cms_friend_site")
public class CmsFriendSite  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "友情链接";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_SITEID = "siteId";
	public static final String ALIAS_NAME = "name";
	public static final String ALIAS_LINKTYPE = "跳出类型,_blank";
	public static final String ALIAS_URL = "网站地址";
	public static final String ALIAS_LOGO = "网站logo";
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
	 * siteId
	 */
	private java.lang.String siteId;
	/**
	 * name
	 */
	private java.lang.String name;
	/**
	 * 跳出类型,_blank
	 */
	private java.lang.String linkType;
	/**
	 * 网站地址
	 */
	private java.lang.String url;
	/**
	 * 网站logo
	 */
	private java.lang.String logo;
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

	public CmsFriendSite(){
	}

	public CmsFriendSite(
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
     @WhereSQL(sql="id=:CmsFriendSite_id")
	public java.lang.String getId() {
		return this.id;
	}
	public void setSiteId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.siteId = value;
	}
	
     @WhereSQL(sql="siteId=:CmsFriendSite_siteId")
	public java.lang.String getSiteId() {
		return this.siteId;
	}
	public void setName(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.name = value;
	}
	
     @WhereSQL(sql="name=:CmsFriendSite_name")
	public java.lang.String getName() {
		return this.name;
	}
	public void setLinkType(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.linkType = value;
	}
	
     @WhereSQL(sql="linkType=:CmsFriendSite_linkType")
	public java.lang.String getLinkType() {
		return this.linkType;
	}
	public void setUrl(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.url = value;
	}
	
     @WhereSQL(sql="url=:CmsFriendSite_url")
	public java.lang.String getUrl() {
		return this.url;
	}
	public void setLogo(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.logo = value;
	}
	
     @WhereSQL(sql="logo=:CmsFriendSite_logo")
	public java.lang.String getLogo() {
		return this.logo;
	}
	public void setSortno(java.lang.Integer value) {
		this.sortno = value;
	}
	
     @WhereSQL(sql="sortno=:CmsFriendSite_sortno")
	public java.lang.Integer getSortno() {
		return this.sortno;
	}
	public void setActive(java.lang.Integer value) {
		this.active = value;
	}
	
     @WhereSQL(sql="active=:CmsFriendSite_active")
	public java.lang.Integer getActive() {
		return this.active;
	}
	
	@Override
    public String toString() {
		return new StringBuilder()
			.append("id[").append(getId()).append("],")
			.append("siteId[").append(getSiteId()).append("],")
			.append("name[").append(getName()).append("],")
			.append("跳出类型,_blank[").append(getLinkType()).append("],")
			.append("网站地址[").append(getUrl()).append("],")
			.append("网站logo[").append(getLogo()).append("],")
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
		if(obj instanceof CmsFriendSite == false){
			return false;
		} 
		if(this == obj){
			return true;
		} 
		CmsFriendSite other = (CmsFriendSite)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

	
