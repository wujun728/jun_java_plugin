package org.springrain.frame.entity;

import java.util.Calendar;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springrain.frame.annotation.TableSuffix;
import org.springrain.frame.annotation.WhereSQL;
import org.springrain.frame.util.GlobalStatic;
/**
 * TODO 在此加入类描述
 * @copyright {@link weicms.net}
 * @author springrain<Auto generate>
 * @version  2013-04-02 10:17:31
 * @see org.springrain.frame.entity.AuditLog
 */
@Table(name="t_auditlog")
@TableSuffix(name="suffix")
public class AuditLog  extends BaseEntity  {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "Auditlog";
	public static final String ALIAS_ID = "ID";
	public static final String ALIAS_OPERATION_TYPE = "操作类型";
	public static final String ALIAS_OPERATOR_NAME = "操作人姓名";
	public static final String ALIAS_PRE_VALUE = "旧值";
	public static final String ALIAS_CUR_VALUE = "新值";
	public static final String ALIAS_OPERATION_TIME = "操作时间";
	public static final String ALIAS_OPERATION_CLASS = "操作类";
	public static final String ALIAS_OPERATION_CLASS_ID = "记录ID";
    */
	//date formats
	//public static final String FORMAT_OPERATION_TIME = DateUtils.DATETIME_FORMAT;
	
	//columns START
	/**
	 * ID
	 */
	private java.lang.String id;
	/**
	 * 操作类型
	 */
	private java.lang.String operationType;
	/**
	 * 操作人姓名
	 */
	private java.lang.String operatorName;
	/**
	 * 旧值
	 */
	private java.lang.String preValue;
	/**
	 * 新值
	 */
	private java.lang.String curValue;
	/**
	 * 操作时间
	 */
	private java.util.Date operationTime;
	/**
	 * 操作类
	 */
	private java.lang.String operationClass;
	/**
	 * 记录ID
	 */
	private java.lang.String operationClassId;
	//columns END
	
	
	private String suffix;
	
	//concstructor



	public AuditLog(){
	}

	public AuditLog(
		java.lang.String id
	){
		this.id = id;
	}

	//get and set
	public void setId(java.lang.String value) {
		this.id = value;
	}
	
	@Id
     @WhereSQL(sql="id=:Auditlog_id")
	public java.lang.String getId() {
		return this.id;
	}
	public void setOperationType(java.lang.String value) {
		this.operationType = value;
	}
	
     @WhereSQL(sql="operationType=:Auditlog_operationType")
	public java.lang.String getOperationType() {
		return this.operationType;
	}
	public void setOperatorName(java.lang.String value) {
		this.operatorName = value;
	}
	
     @WhereSQL(sql="operatorName=:Auditlog_operatorName")
	public java.lang.String getOperatorName() {
		return this.operatorName;
	}
	public void setPreValue(java.lang.String value) {
		this.preValue = value;
	}
	
     @WhereSQL(sql="preValue=:Auditlog_preValue")
	public java.lang.String getPreValue() {
		return this.preValue;
	}
	public void setCurValue(java.lang.String value) {
		this.curValue = value;
	}
	
     @WhereSQL(sql="curValue=:Auditlog_curValue")
	public java.lang.String getCurValue() {
		return this.curValue;
	}
	
	
	public void setOperationTime(java.util.Date value) {
		this.operationTime = value;
	}
	
     @WhereSQL(sql="operationTime=:Auditlog_operationTime")
	public java.util.Date getOperationTime() {
		return this.operationTime;
	}
	public void setOperationClass(java.lang.String value) {
		this.operationClass = value;
	}
	
     @WhereSQL(sql="operationClass=:Auditlog_operationClass")
	public java.lang.String getOperationClass() {
		return this.operationClass;
	}
	public void setOperationClassId(java.lang.String value) {
		this.operationClassId = value;
	}
	
     @WhereSQL(sql="operationClassId=:Auditlog_operationClassId")
	public java.lang.String getOperationClassId() {
		return this.operationClassId;
	}
	
	@Override
    public String toString() {
		return new StringBuilder()
			.append("ID[").append(getId()).append("],")
			.append("操作类型[").append(getOperationType()).append("],")
			.append("操作人姓名[").append(getOperatorName()).append("],")
			.append("旧值[").append(getPreValue()).append("],")
			.append("新值[").append(getCurValue()).append("],")
			.append("操作时间[").append(getOperationTime()).append("],")
			.append("操作类[").append(getOperationClass()).append("],")
			.append("记录ID[").append(getOperationClassId()).append("],")
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
		if(obj instanceof AuditLog == false){
			return false;
		} 
		if(this == obj){
			return true;
		} 
		AuditLog other = (AuditLog)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
	
	@Transient
	public String getSuffix() {
		if(StringUtils.isBlank(suffix)){
			int year= Calendar.getInstance().get(Calendar.YEAR);
			this.suffix= GlobalStatic.tableSuffix + year;
		}
			return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	
}

	
