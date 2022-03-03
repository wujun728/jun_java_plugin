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
 * @version  2017-11-02 10:35:18
 * @see org.springrain.selenium.entity.Htmlfunction
 */
@Table(name="tc_htmlfunction")
public class Htmlfunction  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "Htmlfunction";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_NAME = "功能名称";
	public static final String ALIAS_URL = "功能URL";
	public static final String ALIAS_PID = "pid";
	public static final String ALIAS_FINDTYPE = "0dcoucment,1id,2name,3className,4cssSelector,5linkText,6.tagName,7xpath,8alert";
	public static final String ALIAS_ELEMENTKEY = "元素的Key,例如 userName 或者xpath的表达式";
	public static final String ALIAS_COMPARE = "eq,lt,";
	public static final String ALIAS_ELEMENTVALUE = "期望结果,例如判断网页的标题";
	public static final String ALIAS_XPATH = "实际的xpath表达式";
	public static final String ALIAS_SORTNO = "sortno";
    */
	//date formats
	
	//columns START
	/**
	 * id
	 */
	private java.lang.String id;
	/**
	 * 功能名称
	 */
	private java.lang.String name;
	/**
	 * 功能URL
	 */
	private java.lang.String url;
	/**
	 * pid
	 */
	private java.lang.String pid;
	/**
	 * 0dcoucment,1id,2name,3className,4cssSelector,5linkText,6.tagName,7xpath,8alert
	 */
	private java.lang.Integer findType;
	/**
	 * 元素的Key,例如 userName 或者xpath的表达式
	 */
	private java.lang.String elementKey;
	/**
	 * eq,lt,
	 */
	private java.lang.String compare;
	/**
	 * 期望结果,例如判断网页的标题
	 */
	private java.lang.String elementValue;
	/**
	 * 实际的xpath表达式
	 */
	private java.lang.String xpath;
	/**
	 * sortno
	 */
	private java.lang.Integer sortno;
	//columns END 数据库字段结束
	
	//concstructor

	public Htmlfunction(){
	}

	public Htmlfunction(
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
     @WhereSQL(sql="id=:Htmlfunction_id")
	public java.lang.String getId() {
		return this.id;
	}
		/**
		 * 功能名称
		 */
	public void setName(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.name = value;
	}
	
	
	
	/**
	 * 功能名称
	 */
     @WhereSQL(sql="name=:Htmlfunction_name")
	public java.lang.String getName() {
		return this.name;
	}
		/**
		 * 功能URL
		 */
	public void setUrl(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.url = value;
	}
	
	
	
	/**
	 * 功能URL
	 */
     @WhereSQL(sql="url=:Htmlfunction_url")
	public java.lang.String getUrl() {
		return this.url;
	}
		/**
		 * pid
		 */
	public void setPid(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.pid = value;
	}
	
	
	
	/**
	 * pid
	 */
     @WhereSQL(sql="pid=:Htmlfunction_pid")
	public java.lang.String getPid() {
		return this.pid;
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
     @WhereSQL(sql="findType=:Htmlfunction_findType")
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
     @WhereSQL(sql="elementKey=:Htmlfunction_elementKey")
	public java.lang.String getElementKey() {
		return this.elementKey;
	}
		/**
		 * eq,lt,
		 */
	public void setCompare(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.compare = value;
	}
	
	
	
	/**
	 * eq,lt,
	 */
     @WhereSQL(sql="compare=:Htmlfunction_compare")
	public java.lang.String getCompare() {
		return this.compare;
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
     @WhereSQL(sql="elementValue=:Htmlfunction_elementValue")
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
     @WhereSQL(sql="xpath=:Htmlfunction_xpath")
	public java.lang.String getXpath() {
		return this.xpath;
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
     @WhereSQL(sql="sortno=:Htmlfunction_sortno")
	public java.lang.Integer getSortno() {
		return this.sortno;
	}
	@Override
	public String toString() {
		return new StringBuilder()
			.append("id[").append(getId()).append("],")
			.append("功能名称[").append(getName()).append("],")
			.append("功能URL[").append(getUrl()).append("],")
			.append("pid[").append(getPid()).append("],")
			.append("0dcoucment,1id,2name,3className,4cssSelector,5linkText,6.tagName,7xpath,8alert[").append(getFindType()).append("],")
			.append("元素的Key,例如 userName 或者xpath的表达式[").append(getElementKey()).append("],")
			.append("eq,lt,[").append(getCompare()).append("],")
			.append("期望结果,例如判断网页的标题[").append(getElementValue()).append("],")
			.append("实际的xpath表达式[").append(getXpath()).append("],")
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
		if(obj instanceof Htmlfunction == false){
			return false;
		}
			
		if(this == obj){
			return true;
		}
		
		Htmlfunction other = (Htmlfunction)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

	
