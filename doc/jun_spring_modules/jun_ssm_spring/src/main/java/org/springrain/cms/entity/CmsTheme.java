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
 * @version  2016-11-12 10:45:00
 * @see org.springrain.cms.entity.demo.entity.CmsTheme
 */
@Table(name="cms_theme")
public class CmsTheme  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "主题表";
	public static final String ALIAS_ID = "ID";
	public static final String ALIAS_NAME = "名称";
	public static final String ALIAS_USECOUNT = "使用次数";
	public static final String ALIAS_ACTIVE = "状态 0不可用,1可用";
    */
	//date formats
	
	//columns START
	/**
	 * ID
	 */
	private java.lang.String id;
	/**
	 * 名称
	 */
	private java.lang.String name;
	/**
	 * 使用次数
	 */
	private java.lang.Integer usecount;
	/**
	 * 状态 0不可用,1可用
	 */
	private java.lang.Integer active;
	//columns END 数据库字段结束
	
	//concstructor

	public CmsTheme(){
	}

	public CmsTheme(
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
     @WhereSQL(sql="id=:CmsTheme_id")
	public java.lang.String getId() {
		return this.id;
	}
	public void setName(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.name = value;
	}
	
     @WhereSQL(sql="name=:CmsTheme_name")
	public java.lang.String getName() {
		return this.name;
	}
	public void setUsecount(java.lang.Integer value) {
		this.usecount = value;
	}
	
     @WhereSQL(sql="usecount=:CmsTheme_usecount")
	public java.lang.Integer getUsecount() {
		return this.usecount;
	}
	public void setActive(java.lang.Integer value) {
		this.active = value;
	}
	
     @WhereSQL(sql="active=:CmsTheme_active")
	public java.lang.Integer getActive() {
		return this.active;
	}
	
	@Override
    public String toString() {
		return new StringBuilder()
			.append("ID[").append(getId()).append("],")
			.append("名称[").append(getName()).append("],")
			.append("使用次数[").append(getUsecount()).append("],")
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
		if(obj instanceof CmsTheme == false){
			return false;
		} 
		if(this == obj){
			return true;
		} 
		CmsTheme other = (CmsTheme)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

	
