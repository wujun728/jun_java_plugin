package org.springrain.selenium.entity;

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
 * @version  2017-11-02 10:35:08
 * @see org.springrain.selenium.entity.Htmlfield
 */
@Table(name="tc_htmlfield")
public class Htmlfield  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "Htmlfield";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_FUNCTIONID = "functionId";
	public static final String ALIAS_NAME = "name";
	public static final String ALIAS_FINDTYPE = "0dcoucment,1id,2name,3className,4cssSelector,5linkText,6.tagName,7xpath,8alert";
	public static final String ALIAS_ELEMENTKEY = "元素的Key,例如 userName 或者xpath的表达式";
	public static final String ALIAS_ELEMENTVALUE = "期望结果,例如判断网页的标题";
	public static final String ALIAS_XPATH = "实际的xpath表达式";
	public static final String ALIAS_REQUIRED = "是否必填";
	public static final String ALIAS_HTMLFIELDTYPE = "1text,2password";
	public static final String ALIAS_HTMLFIELDLENGTH = "字段长度";
	public static final String ALIAS_HTMLMINVALUE = "htmlMinValue";
	public static final String ALIAS_HTMLMAXVALUE = "htmlMaxValue";
	public static final String ALIAS_SORTNO = "sortno";
    */
	//date formats
	
	//columns START
	/**
	 * id
	 */
	private java.lang.String id;
	/**
	 * functionId
	 */
	private java.lang.String functionId;
	/**
	 * name
	 */
	private java.lang.String name;
	/**
	 * 0dcoucment,1id,2name,3className,4cssSelector,5linkText,6.tagName,7xpath,8alert
	 */
	private java.lang.Integer findType;
	/**
	 * 元素的Key,例如 userName 或者xpath的表达式
	 */
	private java.lang.String elementKey;
	/**
	 * 期望结果,例如判断网页的标题
	 */
	private java.lang.String elementValue;
	/**
	 * 实际的xpath表达式
	 */
	private java.lang.String xpath;
	/**
	 * 是否必填
	 */
	private java.lang.Integer required;
	/**
	 * 1text,2password
	 */
	private java.lang.Integer htmlFieldType;
	/**
	 * 字段长度
	 */
	private java.lang.Integer htmlFieldLength;
	/**
	 * htmlMinValue
	 */
	private java.math.BigDecimal htmlMinValue;
	/**
	 * htmlMaxValue
	 */
	private java.math.BigDecimal htmlMaxValue;
	/**
	 * sortno
	 */
	private java.lang.Integer sortno;
	//columns END 数据库字段结束
	
	//concstructor

	public Htmlfield(){
	}

	public Htmlfield(
		java.lang.String id
	){
		this.id = id;
	}

	//get and set
		/**
		 * id
		 */
	public void setId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.id = value;
	}
	
	
	
	/**
	 * id
	 */
	@Id
     @WhereSQL(sql="id=:Htmlfield_id")
	public java.lang.String getId() {
		return this.id;
	}
		/**
		 * functionId
		 */
	public void setFunctionId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.functionId = value;
	}
	
	
	
	/**
	 * functionId
	 */
     @WhereSQL(sql="functionId=:Htmlfield_functionId")
	public java.lang.String getFunctionId() {
		return this.functionId;
	}
		/**
		 * name
		 */
	public void setName(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.name = value;
	}
	
	
	
	/**
	 * name
	 */
     @WhereSQL(sql="name=:Htmlfield_name")
	public java.lang.String getName() {
		return this.name;
	}
		/**
		 * 0dcoucment,1id,2name,3className,4cssSelector,5linkText,6.tagName,7xpath,8alert
		 */
	public void setFindType(java.lang.Integer value) {
		this.findType = value;
	}
	
	
	
	/**
	 * 0dcoucment,1id,2name,3className,4cssSelector,5linkText,6.tagName,7xpath,8alert
	 */
     @WhereSQL(sql="findType=:Htmlfield_findType")
	public java.lang.Integer getFindType() {
		return this.findType;
	}
		/**
		 * 元素的Key,例如 userName 或者xpath的表达式
		 */
	public void setElementKey(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.elementKey = value;
	}
	
	
	
	/**
	 * 元素的Key,例如 userName 或者xpath的表达式
	 */
     @WhereSQL(sql="elementKey=:Htmlfield_elementKey")
	public java.lang.String getElementKey() {
		return this.elementKey;
	}
		/**
		 * 期望结果,例如判断网页的标题
		 */
	public void setElementValue(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.elementValue = value;
	}
	
	
	
	/**
	 * 期望结果,例如判断网页的标题
	 */
     @WhereSQL(sql="elementValue=:Htmlfield_elementValue")
	public java.lang.String getElementValue() {
		return this.elementValue;
	}
		/**
		 * 实际的xpath表达式
		 */
	public void setXpath(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.xpath = value;
	}
	
	
	
	/**
	 * 实际的xpath表达式
	 */
     @WhereSQL(sql="xpath=:Htmlfield_xpath")
	public java.lang.String getXpath() {
		return this.xpath;
	}
		/**
		 * 是否必填
		 */
	public void setRequired(java.lang.Integer value) {
		this.required = value;
	}
	
	
	
	/**
	 * 是否必填
	 */
     @WhereSQL(sql="required=:Htmlfield_required")
	public java.lang.Integer getRequired() {
		return this.required;
	}
		/**
		 * 1text,2password
		 */
	public void setHtmlFieldType(java.lang.Integer value) {
		this.htmlFieldType = value;
	}
	
	
	
	/**
	 * 1text,2password
	 */
     @WhereSQL(sql="htmlFieldType=:Htmlfield_htmlFieldType")
	public java.lang.Integer getHtmlFieldType() {
		return this.htmlFieldType;
	}
		/**
		 * 字段长度
		 */
	public void setHtmlFieldLength(java.lang.Integer value) {
		this.htmlFieldLength = value;
	}
	
	
	
	/**
	 * 字段长度
	 */
     @WhereSQL(sql="htmlFieldLength=:Htmlfield_htmlFieldLength")
	public java.lang.Integer getHtmlFieldLength() {
		return this.htmlFieldLength;
	}
		/**
		 * htmlMinValue
		 */
	public void setHtmlMinValue(java.math.BigDecimal value) {
		this.htmlMinValue = value;
	}
	
	
	
	/**
	 * htmlMinValue
	 */
     @WhereSQL(sql="htmlMinValue=:Htmlfield_htmlMinValue")
	public java.math.BigDecimal getHtmlMinValue() {
		return this.htmlMinValue;
	}
		/**
		 * htmlMaxValue
		 */
	public void setHtmlMaxValue(java.math.BigDecimal value) {
		this.htmlMaxValue = value;
	}
	
	
	
	/**
	 * htmlMaxValue
	 */
     @WhereSQL(sql="htmlMaxValue=:Htmlfield_htmlMaxValue")
	public java.math.BigDecimal getHtmlMaxValue() {
		return this.htmlMaxValue;
	}
		/**
		 * sortno
		 */
	public void setSortno(java.lang.Integer value) {
		this.sortno = value;
	}
	
	
	
	/**
	 * sortno
	 */
     @WhereSQL(sql="sortno=:Htmlfield_sortno")
	public java.lang.Integer getSortno() {
		return this.sortno;
	}
	@Override
	public String toString() {
		return new StringBuilder()
			.append("id[").append(getId()).append("],")
			.append("functionId[").append(getFunctionId()).append("],")
			.append("name[").append(getName()).append("],")
			.append("0dcoucment,1id,2name,3className,4cssSelector,5linkText,6.tagName,7xpath,8alert[").append(getFindType()).append("],")
			.append("元素的Key,例如 userName 或者xpath的表达式[").append(getElementKey()).append("],")
			.append("期望结果,例如判断网页的标题[").append(getElementValue()).append("],")
			.append("实际的xpath表达式[").append(getXpath()).append("],")
			.append("是否必填[").append(getRequired()).append("],")
			.append("1text,2password[").append(getHtmlFieldType()).append("],")
			.append("字段长度[").append(getHtmlFieldLength()).append("],")
			.append("htmlMinValue[").append(getHtmlMinValue()).append("],")
			.append("htmlMaxValue[").append(getHtmlMaxValue()).append("],")
			.append("sortno[").append(getSortno()).append("],")
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
		if(obj instanceof Htmlfield == false){
			return false;
		}
			
		if(this == obj){
			return true;
		}
		
		Htmlfield other = (Htmlfield)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

	
