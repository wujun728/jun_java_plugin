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
 * @version  2017-11-02 10:34:44
 * @see org.springrain.selenium.entity.Htmlcase
 */
@Table(name="tc_htmlcase")
public class Htmlcase  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "Htmlcase";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_FUCTIONID = "功能ID";
	public static final String ALIAS_RULEID = "ruleId";
	public static final String ALIAS_CASECODE = "测试用例编码,例如 001";
	public static final String ALIAS_HTMLFIELDVALUE = "htmlFieldValue";
	public static final String ALIAS_REALRESULT = "realResult";
	public static final String ALIAS_SUCCESSRESULT = "successResult";
	public static final String ALIAS_PASS = "是否通过";
	public static final String ALIAS_SORTNO = "sortno";
    */
	//date formats
	
	//columns START
	/**
	 * id
	 */
	private java.lang.String id;
	/**
	 * 功能ID
	 */
	private java.lang.String fuctionId;
	/**
	 * ruleId
	 */
	private java.lang.String ruleId;
	/**
	 * 测试用例编码,例如 001
	 */
	private java.lang.String caseCode;
	/**
	 * htmlFieldValue
	 */
	private java.lang.String htmlFieldValue;
	/**
	 * realResult
	 */
	private java.lang.String realResult;
	/**
	 * successResult
	 */
	private java.lang.String successResult;
	/**
	 * 是否通过
	 */
	private java.lang.Integer pass;
	/**
	 * sortno
	 */
	private java.lang.Integer sortno;
	//columns END 数据库字段结束
	
	//concstructor

	public Htmlcase(){
	}

	public Htmlcase(
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
     @WhereSQL(sql="id=:Htmlcase_id")
	public java.lang.String getId() {
		return this.id;
	}
		/**
		 * 功能ID
		 */
	public void setFuctionId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.fuctionId = value;
	}
	
	
	
	/**
	 * 功能ID
	 */
     @WhereSQL(sql="fuctionId=:Htmlcase_fuctionId")
	public java.lang.String getFuctionId() {
		return this.fuctionId;
	}
		/**
		 * ruleId
		 */
	public void setRuleId(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.ruleId = value;
	}
	
	
	
	/**
	 * ruleId
	 */
     @WhereSQL(sql="ruleId=:Htmlcase_ruleId")
	public java.lang.String getRuleId() {
		return this.ruleId;
	}
		/**
		 * 测试用例编码,例如 001
		 */
	public void setCaseCode(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.caseCode = value;
	}
	
	
	
	/**
	 * 测试用例编码,例如 001
	 */
     @WhereSQL(sql="caseCode=:Htmlcase_caseCode")
	public java.lang.String getCaseCode() {
		return this.caseCode;
	}
		/**
		 * htmlFieldValue
		 */
	public void setHtmlFieldValue(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.htmlFieldValue = value;
	}
	
	
	
	/**
	 * htmlFieldValue
	 */
     @WhereSQL(sql="htmlFieldValue=:Htmlcase_htmlFieldValue")
	public java.lang.String getHtmlFieldValue() {
		return this.htmlFieldValue;
	}
		/**
		 * realResult
		 */
	public void setRealResult(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.realResult = value;
	}
	
	
	
	/**
	 * realResult
	 */
     @WhereSQL(sql="realResult=:Htmlcase_realResult")
	public java.lang.String getRealResult() {
		return this.realResult;
	}
		/**
		 * successResult
		 */
	public void setSuccessResult(java.lang.String value) {
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		this.successResult = value;
	}
	
	
	
	/**
	 * successResult
	 */
     @WhereSQL(sql="successResult=:Htmlcase_successResult")
	public java.lang.String getSuccessResult() {
		return this.successResult;
	}
		/**
		 * 是否通过
		 */
	public void setPass(java.lang.Integer value) {
		this.pass = value;
	}
	
	
	
	/**
	 * 是否通过
	 */
     @WhereSQL(sql="pass=:Htmlcase_pass")
	public java.lang.Integer getPass() {
		return this.pass;
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
     @WhereSQL(sql="sortno=:Htmlcase_sortno")
	public java.lang.Integer getSortno() {
		return this.sortno;
	}
	@Override
	public String toString() {
		return new StringBuilder()
			.append("id[").append(getId()).append("],")
			.append("功能ID[").append(getFuctionId()).append("],")
			.append("ruleId[").append(getRuleId()).append("],")
			.append("测试用例编码,例如 001[").append(getCaseCode()).append("],")
			.append("htmlFieldValue[").append(getHtmlFieldValue()).append("],")
			.append("realResult[").append(getRealResult()).append("],")
			.append("successResult[").append(getSuccessResult()).append("],")
			.append("是否通过[").append(getPass()).append("],")
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
		if(obj instanceof Htmlcase == false){
			return false;
		}
			
		if(this == obj){
			return true;
		}
		
		Htmlcase other = (Htmlcase)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

	
