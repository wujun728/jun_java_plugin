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
 * @version  2016-11-12 11:09:41
 * @see org.springrain.cms.entity.demo.entity.CmsLink
 */
@Table(name="cms_link")
public class CmsLink  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "业务链接表";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_NAME = "name";
	public static final String ALIAS_DEFAULTLINK = "默认URL地址";
	public static final String ALIAS_LINK = "使用的URL";
	public static final String ALIAS_SITEID = "网站ID";
	public static final String ALIAS_BUSINESSID = "业务Id";
	public static final String ALIAS_LOOKCOUNT = "打开次数";
	public static final String ALIAS_MODELTYPE = " 0使用freemarker模板,1redirect,2forward";
	public static final String ALIAS_FTLFILE = "当前渲染使用的模板路径";
	public static final String ALIAS_NODEFTLFILE = "子内容使用的ftl模板文件";
	public static final String ALIAS_STATICHTML = "是否静态化 0否,1是";
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
	 * name
	 */
	private java.lang.String name;
	/**
	 * 默认URL地址
	 */
	private java.lang.String defaultLink;
	/**
	 * 使用的URL
	 */
	private java.lang.String link;
	/**
	 * 网站ID
	 */
	private java.lang.String siteId;
	/**
	 * 业务Id
	 */
	private java.lang.String businessId;
	/**
	 * 打开次数
	 */
	private java.lang.Integer lookcount;
	/**
	 * 0使用freemarker模板,1redirect,2forward
	 */
	private java.lang.Integer jumpType;
	/**
	 * 当前渲染使用的模板路径
	 */
	private java.lang.String ftlfile;
	/**
	 * 子内容使用的ftl模板文件
	 */
	private java.lang.String nodeftlfile;
	/**
	 * 是否静态化 0否,1是
	 */
	private java.lang.Integer statichtml;
	/**
	 * 排序
	 */
	private java.lang.Integer sortno;
	/**
	 * 状态 0不可用,1可用
	 */
	private java.lang.Integer active;
	
	/**
	 * 是否需要登录访问  0否 1是
	 */
	private java.lang.Integer loginuser;
	
	private java.lang.Integer modelType;
	
	private Integer siteType;
	
	//columns END 数据库字段结束
	
	//concstructor

	public CmsLink(){
	}

	public CmsLink(
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
     @WhereSQL(sql="id=:CmsLink_id")
	public java.lang.String getId() {
		return this.id;
	}
	public void setName(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.name = value;
	}
	
     @WhereSQL(sql="name=:CmsLink_name")
	public java.lang.String getName() {
		return this.name;
	}
	public void setDefaultLink(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.defaultLink = value;
	}
	
     @WhereSQL(sql="defaultLink=:CmsLink_defaultLink")
	public java.lang.String getDefaultLink() {
		return this.defaultLink;
	}
	public void setLink(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.link = value;
	}
	
     @WhereSQL(sql="link=:CmsLink_link")
	public java.lang.String getLink() {
		return this.link;
	}
	public void setSiteId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.siteId = value;
	}
	
     @WhereSQL(sql="siteId=:CmsLink_siteId")
	public java.lang.String getSiteId() {
		return this.siteId;
	}
	public void setBusinessId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.businessId = value;
	}
	
     @WhereSQL(sql="businessId=:CmsLink_businessId")
	public java.lang.String getBusinessId() {
		return this.businessId;
	}
	public void setLookcount(java.lang.Integer value) {
		this.lookcount = value;
	}
	
     @WhereSQL(sql="lookcount=:CmsLink_lookcount")
	public java.lang.Integer getLookcount() {
		return this.lookcount;
	}
	public void setJumpType(java.lang.Integer value) {
		this.jumpType = value;
	}
	
     @WhereSQL(sql="jumpType=:CmsLink_jumpType")
	public java.lang.Integer getJumpType() {
		return this.jumpType;
	}
	public void setFtlfile(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.ftlfile = value;
	}
	
     @WhereSQL(sql="ftlfile=:CmsLink_ftlfile")
	public java.lang.String getFtlfile() {
		return this.ftlfile;
	}
	public void setNodeftlfile(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.nodeftlfile = value;
	}
	
     @WhereSQL(sql="nodeftlfile=:CmsLink_nodeftlfile")
	public java.lang.String getNodeftlfile() {
		return this.nodeftlfile;
	}
	public void setStatichtml(java.lang.Integer value) {
		this.statichtml = value;
	}
	
     @WhereSQL(sql="statichtml=:CmsLink_statichtml")
	public java.lang.Integer getStatichtml() {
		return this.statichtml;
	}
	public void setSortno(java.lang.Integer value) {
		this.sortno = value;
	}
	
     @WhereSQL(sql="sortno=:CmsLink_sortno")
	public java.lang.Integer getSortno() {
		return this.sortno;
	}
	public void setActive(java.lang.Integer value) {
		this.active = value;
	}
	
     @WhereSQL(sql="active=:CmsLink_active")
	public java.lang.Integer getActive() {
		return this.active;
	}
     @WhereSQL(sql="loginuser=:CmsLink_loginuser")
	public java.lang.Integer getLoginuser() {
		return loginuser;
	}

	public void setLoginuser(java.lang.Integer loginuser) {
		this.loginuser = loginuser;
	}
	 @WhereSQL(sql="modelType=:CmsLink_modelType")
	public java.lang.Integer getModelType() {
		return modelType;
	}

	public void setModelType(java.lang.Integer modelType) {
		this.modelType = modelType;
	}
	@WhereSQL(sql="siteType=:CmsLink_siteType")
	public Integer getSiteType() {
		return siteType;
	}

	public void setSiteType(Integer siteType) {
		this.siteType = siteType;
	}

	@Override
    public String toString() {
		return new StringBuilder()
			.append("id[").append(getId()).append("],")
			.append("name[").append(getName()).append("],")
			.append("默认URL地址[").append(getDefaultLink()).append("],")
			.append("使用的URL[").append(getLink()).append("],")
			.append("网站ID[").append(getSiteId()).append("],")
			.append("业务Id[").append(getBusinessId()).append("],")
			.append("打开次数[").append(getLookcount()).append("],")
			.append("0使用freemarker模板,1redirect,2forward[").append(getJumpType()).append("],")
			.append("当前渲染使用的模板路径[").append(getFtlfile()).append("],")
			.append("子内容使用的ftl模板文件[").append(getNodeftlfile()).append("],")
			.append("是否静态化 0否,1是[").append(getStatichtml()).append("],")
			.append("排序[").append(getSortno()).append("],")
			.append("状态 0不可用,1可用[").append(getActive()).append("],")
			.append("modelType[").append(getModelType()).append("],")
			.append("siteType[").append(getSiteType()).append("],")
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
		if(obj instanceof CmsLink == false){
			return false;
		} 
		if(this == obj){
			return true;
		} 
		CmsLink other = (CmsLink)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

	
