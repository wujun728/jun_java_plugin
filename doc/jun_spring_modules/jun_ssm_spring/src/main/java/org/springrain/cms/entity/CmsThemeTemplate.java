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
 * @see org.springrain.cms.entity.demo.entity.CmsThemeTemplate
 */
@Table(name="cms_theme_template")
public class CmsThemeTemplate  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "主题和模板中间表";
	public static final String ALIAS_ID = "ID";
	public static final String ALIAS_THEMEID = "主题Id";
	public static final String ALIAS_TEMPLATEID = "模板Id";
    */
	//date formats
	
	//columns START
	/**
	 * ID
	 */
	private java.lang.String id;
	/**
	 * 主题Id
	 */
	private java.lang.String themeId;
	/**
	 * 模板Id
	 */
	private java.lang.String templateId;
	//columns END 数据库字段结束
	
	//concstructor

	public CmsThemeTemplate(){
	}

	public CmsThemeTemplate(
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
     @WhereSQL(sql="id=:CmsThemeTemplate_id")
	public java.lang.String getId() {
		return this.id;
	}
	public void setThemeId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.themeId = value;
	}
	
     @WhereSQL(sql="themeId=:CmsThemeTemplate_themeId")
	public java.lang.String getThemeId() {
		return this.themeId;
	}
	public void setTemplateId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.templateId = value;
	}
	
     @WhereSQL(sql="templateId=:CmsThemeTemplate_templateId")
	public java.lang.String getTemplateId() {
		return this.templateId;
	}
	
	@Override
    public String toString() {
		return new StringBuilder()
			.append("ID[").append(getId()).append("],")
			.append("主题Id[").append(getThemeId()).append("],")
			.append("模板Id[").append(getTemplateId()).append("],")
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
		if(obj instanceof CmsThemeTemplate == false){
			return false;
		} 
		if(this == obj){
			return true;
		} 
		CmsThemeTemplate other = (CmsThemeTemplate)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

	
