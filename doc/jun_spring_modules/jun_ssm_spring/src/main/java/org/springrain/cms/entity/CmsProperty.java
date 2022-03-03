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
 * @see org.springrain.cms.entity.demo.entity.CmsProperty
 */
@Table(name="cms_property")
public class CmsProperty  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "自定义属性表";
	public static final String ALIAS_ID = "siteId_code,保证一个站点下code唯一";
	public static final String ALIAS_SITEID = "siteId";
	public static final String ALIAS_NAME = "name";
	public static final String ALIAS_CODE = "系统级别的编码,一个站点不可重复";
	public static final String ALIAS_INPUTTYPE = "0text,1date,2datatime,3int,4float,5select,6file,7img,8imgs";
	public static final String ALIAS_BUSINESSID = "业务Id";
	public static final String ALIAS_MODELTYPE = "0site,1channel,2content,3投票(以后可能扩展更多系统功能,例如 注册 登陆 订单 购物车)";
	public static final String ALIAS_CREATEPERSON = "创建人";
	public static final String ALIAS_CREATEDATE = "创建时间";
	public static final String ALIAS_DEFAULTVALUE = "默认值";
	public static final String ALIAS_STYLE = "样式";
	public static final String ALIAS_SORTNO = "排序";
	public static final String ALIAS_ACTIVE = "状态 0不可用,1可用";
    */
	//date formats
	//public static final String FORMAT_CREATEDATE = DateUtils.DATETIME_FORMAT;
	
	//columns START
	/**
	 * siteId_code,保证一个站点下code唯一
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
	 * 系统级别的编码,一个站点不可重复
	 */
	private java.lang.String code;
	/**
	 * 0text,1date,2datatime,3int,4float,5select,6file,7img,8imgs
	 */
	private java.lang.Integer inputType;
	/**
	 * 业务Id
	 */
	private java.lang.String businessId;
	/**
	 * 0site,1channel,2content,3投票(以后可能扩展更多系统功能,例如 注册 登陆 订单 购物车)
	 */
	private java.lang.Integer modelType;
	/**
	 * 创建人
	 */
	private java.lang.String createPerson;
	/**
	 * 创建时间
	 */
	private java.util.Date createDate;
	/**
	 * 默认值
	 */
	private java.lang.String defaultValue;
	/**
	 * pvalue
	 */
	private java.lang.String pvalue;
	
	//子是否可用
	private Integer leafUse;
	
	
	/**
	 * 样式
	 */
	private java.lang.String style;
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

	public CmsProperty(){
	}

	public CmsProperty(
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
     @WhereSQL(sql="id=:CmsProperty_id")
	public java.lang.String getId() {
		return this.id;
	}
	public void setSiteId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.siteId = value;
	}
	
     @WhereSQL(sql="siteId=:CmsProperty_siteId")
	public java.lang.String getSiteId() {
		return this.siteId;
	}
	public void setName(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.name = value;
	}
	
     @WhereSQL(sql="name=:CmsProperty_name")
	public java.lang.String getName() {
		return this.name;
	}
	public void setCode(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.code = value;
	}
	
     @WhereSQL(sql="code=:CmsProperty_code")
	public java.lang.String getCode() {
		return this.code;
	}
	public void setInputType(java.lang.Integer value) {
		this.inputType = value;
	}
	
     @WhereSQL(sql="inputType=:CmsProperty_inputType")
	public java.lang.Integer getInputType() {
		return this.inputType;
	}
	public void setBusinessId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.businessId = value;
	}
	
     @WhereSQL(sql="businessId=:CmsProperty_businessId")
	public java.lang.String getBusinessId() {
		return this.businessId;
	}
	public void setModelType(java.lang.Integer value) {
		this.modelType = value;
	}
	
     @WhereSQL(sql="modelType=:CmsProperty_modelType")
	public java.lang.Integer getModelType() {
		return this.modelType;
	}
	public void setCreatePerson(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.createPerson = value;
	}
	
     @WhereSQL(sql="createPerson=:CmsProperty_createPerson")
	public java.lang.String getCreatePerson() {
		return this.createPerson;
	}
		/*
	public String getcreateDateString() {
		return DateUtils.convertDate2String(FORMAT_CREATEDATE, getcreateDate());
	}
	public void setcreateDateString(String value) throws ParseException{
		setcreateDate(DateUtils.convertString2Date(FORMAT_CREATEDATE,value));
	}*/
	
	public void setCreateDate(java.util.Date value) {
		this.createDate = value;
	}
	
     @WhereSQL(sql="createDate=:CmsProperty_createDate")
	public java.util.Date getCreateDate() {
		return this.createDate;
	}
	public void setDefaultValue(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.defaultValue = value;
	}
	
     @WhereSQL(sql="defaultValue=:CmsProperty_defaultValue")
	public java.lang.String getDefaultValue() {
		return this.defaultValue;
	}
	public void setStyle(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.style = value;
	}
	
     @WhereSQL(sql="style=:CmsProperty_style")
	public java.lang.String getStyle() {
		return this.style;
	}
	public void setSortno(java.lang.Integer value) {
		this.sortno = value;
	}
	
	public void setPvalue(java.lang.String value) {
	    if(StringUtils.isNotBlank(value)){
		 value=value.trim();
		}
	this.pvalue = value;
}

	 @WhereSQL(sql="pvalue=:CmsProperty_pvalue")
	public java.lang.String getPvalue() {
		return this.pvalue;
	}
		
	 @WhereSQL(sql="leafUse=:CmsProperty_leafUse")
	public Integer getLeafUse() {
		return leafUse;
	}
	
	public void setLeafUse(Integer leafUse) {
		this.leafUse = leafUse;
	}

	@WhereSQL(sql="sortno=:CmsProperty_sortno")
	public java.lang.Integer getSortno() {
		return this.sortno;
	}
	public void setActive(java.lang.Integer value) {
		this.active = value;
	}
	
     @WhereSQL(sql="active=:CmsProperty_active")
	public java.lang.Integer getActive() {
		return this.active;
	}
	
	@Override
    public String toString() {
		return new StringBuilder()
			.append("siteId_code,保证一个站点下code唯一[").append(getId()).append("],")
			.append("siteId[").append(getSiteId()).append("],")
			.append("name[").append(getName()).append("],")
			.append("系统级别的编码,一个站点不可重复[").append(getCode()).append("],")
			.append("0text,1date,2datatime,3int,4float,5select,6file,7img,8imgs[").append(getInputType()).append("],")
			.append("业务Id[").append(getBusinessId()).append("],")
			.append("0site,1channel,2content,3投票(以后可能扩展更多系统功能,例如 注册 登陆 订单 购物车)[").append(getModelType()).append("],")
			.append("创建人[").append(getCreatePerson()).append("],")
			.append("创建时间[").append(getCreateDate()).append("],")
			.append("默认值[").append(getDefaultValue()).append("],")
			.append("样式[").append(getStyle()).append("],")
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
		if(obj instanceof CmsProperty == false){
			return false;
		} 
		if(this == obj){
			return true;
		} 
		CmsProperty other = (CmsProperty)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

	
