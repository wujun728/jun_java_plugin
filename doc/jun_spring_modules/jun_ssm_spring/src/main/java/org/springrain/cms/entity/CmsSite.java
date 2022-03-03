package org.springrain.cms.entity;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

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
 * @see org.springrain.cms.entity.demo.entity.CmsSite
 */
@Table(name="cms_site")
public class CmsSite  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "站点表";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_USERID = "用户Id";
	public static final String ALIAS_NAME = "名称";
	public static final String ALIAS_TITLE = "title";
	public static final String ALIAS_DOMAINURL = "网站域名";
	public static final String ALIAS_LOGO = "网站logo";
	public static final String ALIAS_FOOTER = "页脚";
	public static final String ALIAS_QQ = "QQ";
	public static final String ALIAS_PHONE = "电话";
	public static final String ALIAS_CONTACTS = "联系人";
	public static final String ALIAS_KEYWORDS = "keywords";
	public static final String ALIAS_DESCRIPTION = "description";
	public static final String ALIAS_THEMEID = "主题Id";
	public static final String ALIAS_LOOKCOUNT = "打开次数";
	public static final String ALIAS_SITETYPE = "0微信订阅服务号,1wap,2网站   ";
	public static final String ALIAS_ACTIVE = "状态 0不可用,1可用";
    */
	//date formats
	
	//columns START
	/**
	 * id
	 */
	private java.lang.String id;
	/**
	 * 用户Id
	 */
	private java.lang.String userId;
	/**
	 * 名称
	 */
	private java.lang.String name;
	/**
	 * title
	 */
	private java.lang.String title;
	/**
	 * 网站域名
	 */
	private java.lang.String domainurl;
	/**
	 * 网站logo
	 */
	private java.lang.String logo;
	/**
	 * 页脚
	 */
	private java.lang.String footer;
	/**
	 * QQ
	 */
	private java.lang.String qq;
	/**
	 * 电话
	 */
	private java.lang.String phone;
	/**
	 * 联系人
	 */
	private java.lang.String contacts;
	/**
	 * keywords
	 */
	private java.lang.String keywords;
	/**
	 * description
	 */
	private java.lang.String description;
	/**
	 * 主题Id
	 */
	private java.lang.String themeId;
	/**
	 * 打开次数
	 */
	private java.lang.Integer lookcount;
	/**
	 * 0微信订阅服务号,1wap,2网站   
	 */
	private java.lang.Integer siteType;
	/**
	 * 状态 0不可用,1可用
	 */
	private java.lang.Integer active;
	
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 修改时间
	 */
	private java.util.Date modifyTime;
	
	/**
	 * springbeanid
	 */
	private java.lang.String springbeanid;
	
	/**
	 * 部门关联字段
	 */
	private java.lang.String orgId;
	//columns END 数据库字段结束
	private java.lang.String link;
	private String themeName;//主题名称
	//concstructor

	

	public CmsSite(){
	}

	public CmsSite(
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
     @WhereSQL(sql="id=:CmsSite_id")
	public java.lang.String getId() {
		return this.id;
	}
	public void setUserId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.userId = value;
	}
	
     @WhereSQL(sql="userId=:CmsSite_userId")
	public java.lang.String getUserId() {
		return this.userId;
	}
	public void setName(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.name = value;
	}
	
     @WhereSQL(sql="name=:CmsSite_name")
	public java.lang.String getName() {
		return this.name;
	}
	public void setTitle(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.title = value;
	}
	
     @WhereSQL(sql="title=:CmsSite_title")
	public java.lang.String getTitle() {
		return this.title;
	}
	public void setDomainurl(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.domainurl = value;
	}
	
     @WhereSQL(sql="domainurl=:CmsSite_domainurl")
	public java.lang.String getDomainurl() {
		return this.domainurl;
	}
	public void setLogo(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.logo = value;
	}
	
     @WhereSQL(sql="logo=:CmsSite_logo")
	public java.lang.String getLogo() {
		return this.logo;
	}
	public void setFooter(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.footer = value;
	}
	
     @WhereSQL(sql="footer=:CmsSite_footer")
	public java.lang.String getFooter() {
		return this.footer;
	}
	public void setQq(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.qq = value;
	}
	
     @WhereSQL(sql="qq=:CmsSite_qq")
	public java.lang.String getQq() {
		return this.qq;
	}
	public void setPhone(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.phone = value;
	}
	
     @WhereSQL(sql="phone=:CmsSite_phone")
	public java.lang.String getPhone() {
		return this.phone;
	}
	public void setContacts(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.contacts = value;
	}
	
     @WhereSQL(sql="contacts=:CmsSite_contacts")
	public java.lang.String getContacts() {
		return this.contacts;
	}
	public void setKeywords(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.keywords = value;
	}
	
     @WhereSQL(sql="keywords=:CmsSite_keywords")
	public java.lang.String getKeywords() {
		return this.keywords;
	}
	public void setDescription(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.description = value;
	}
	
     @WhereSQL(sql="description=:CmsSite_description")
	public java.lang.String getDescription() {
		return this.description;
	}
	public void setThemeId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.themeId = value;
	}
	
     @WhereSQL(sql="themeId=:CmsSite_themeId")
	public java.lang.String getThemeId() {
		return this.themeId;
	}
	public void setLookcount(java.lang.Integer value) {
		this.lookcount = value;
	}
	
     @WhereSQL(sql="lookcount=:CmsSite_lookcount")
	public java.lang.Integer getLookcount() {
		return this.lookcount;
	}
	public void setSiteType(java.lang.Integer value) {
		this.siteType = value;
	}
	
     @WhereSQL(sql="siteType=:CmsSite_siteType")
	public java.lang.Integer getSiteType() {
		return this.siteType;
	}
	public void setActive(java.lang.Integer value) {
		this.active = value;
	}
	
     @WhereSQL(sql="active=:CmsSite_active")
	public java.lang.Integer getActive() {
		return this.active;
	}
     @WhereSQL(sql="createTime=:CmsSite_createTime")
	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	@WhereSQL(sql="modifyTime=:CmsSite_modifyTime")
	public java.util.Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(java.util.Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	@WhereSQL(sql="springbeanid=:CmsSite_springbeanid")
	public java.lang.String getSpringbeanid() {
		return springbeanid;
	}
	
	public void setSpringbeanid(java.lang.String springbeanid) {
		this.springbeanid = springbeanid;
	}
	@WhereSQL(sql="orgId=:CmsSite_orgId")
	public java.lang.String getOrgId() {
		return orgId;
	}

	public void setOrgId(java.lang.String orgId) {
		this.orgId = orgId;
	}

	@Transient
	public java.lang.String getLink() {
		return link;
	}

	public void setLink(java.lang.String link) {
		this.link = link;
	}

	
	@Transient
	public String getThemeName() {
		return themeName;
	}

	public void setThemeName(String themeName) {
		this.themeName = themeName;
	}

	@Override
    public String toString() {
		return new StringBuilder()
			.append("id[").append(getId()).append("],")
			.append("创建时间[").append(getCreateTime()).append("],")
			.append("修改时间[").append(getModifyTime()).append("],")
			.append("用户Id[").append(getUserId()).append("],")
			.append("名称[").append(getName()).append("],")
			.append("title[").append(getTitle()).append("],")
			.append("网站域名[").append(getDomainurl()).append("],")
			.append("网站logo[").append(getLogo()).append("],")
			.append("页脚[").append(getFooter()).append("],")
			.append("QQ[").append(getQq()).append("],")
			.append("电话[").append(getPhone()).append("],")
			.append("联系人[").append(getContacts()).append("],")
			.append("keywords[").append(getKeywords()).append("],")
			.append("description[").append(getDescription()).append("],")
			.append("主题Id[").append(getThemeId()).append("],")
			.append("打开次数[").append(getLookcount()).append("],")
			.append("0微信订阅服务号,1wap,2网站   [").append(getSiteType()).append("],")
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
		if(obj instanceof CmsSite == false){
			return false;
		} 
		if(this == obj){
			return true;
		} 
		CmsSite other = (CmsSite)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

	
