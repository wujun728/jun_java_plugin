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
 * @version  2016-11-12 10:44:55
 * @see org.springrain.cms.entity.demo.entity.CmsChannelContent
 */
@Table(name="cms_channel_content")
public class CmsChannelContent  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "栏目内容中间表";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_SITEID = "siteId";
	public static final String ALIAS_CHANNELID = "channelId";
	public static final String ALIAS_CONTENTID = "contentId";
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
	 * channelId
	 */
	private java.lang.String channelId;
	/**
	 * contentId
	 */
	private java.lang.String contentId;
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

	public CmsChannelContent(){
	}

	public CmsChannelContent(
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
     @WhereSQL(sql="id=:CmsChannelContent_id")
	public java.lang.String getId() {
		return this.id;
	}
	public void setSiteId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.siteId = value;
	}
	
     @WhereSQL(sql="siteId=:CmsChannelContent_siteId")
	public java.lang.String getSiteId() {
		return this.siteId;
	}
	public void setChannelId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.channelId = value;
	}
	
     @WhereSQL(sql="channelId=:CmsChannelContent_channelId")
	public java.lang.String getChannelId() {
		return this.channelId;
	}
	public void setContentId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.contentId = value;
	}
	
     @WhereSQL(sql="contentId=:CmsChannelContent_contentId")
	public java.lang.String getContentId() {
		return this.contentId;
	}
	public void setSortno(java.lang.Integer value) {
		this.sortno = value;
	}
	
     @WhereSQL(sql="sortno=:CmsChannelContent_sortno")
	public java.lang.Integer getSortno() {
		return this.sortno;
	}
	public void setActive(java.lang.Integer value) {
		this.active = value;
	}
	
     @WhereSQL(sql="active=:CmsChannelContent_active")
	public java.lang.Integer getActive() {
		return this.active;
	}
	
	@Override
    public String toString() {
		return new StringBuilder()
			.append("id[").append(getId()).append("],")
			.append("siteId[").append(getSiteId()).append("],")
			.append("channelId[").append(getChannelId()).append("],")
			.append("contentId[").append(getContentId()).append("],")
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
		if(obj instanceof CmsChannelContent == false){
			return false;
		} 
		if(this == obj){
			return true;
		} 
		CmsChannelContent other = (CmsChannelContent)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

	
