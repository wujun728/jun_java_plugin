package com.erp.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SysField entity. @author Wujun
 */
@Entity
@Table(name = "sys_field")
public class SysField implements java.io.Serializable {

	// Fields

	private BigDecimal fieldId;
	private String fieldCode;
	private BigDecimal fieldEntityId;
	private String fieldName;
//	private String fieldTablename;
	private String fieldChinaName;
	private String fieldIsNull;
	private String fieldIsKey;
	private String fieldType;
	private String fieldLen;
	private String fieldDict;
	private BigDecimal fieldSort;
	private String fieldState;
	private String fieldIsMark;
	private String operatorName;
	private Timestamp operatorTime;
	private String approveName;
	private Timestamp approveTime;
	private String deleteMark;
	private String deleteMarkBack;

	// Constructors

	/** default constructor */
	public SysField() {
	}

	/** minimal constructor */
	public SysField(BigDecimal fieldId) {
		this.fieldId = fieldId;
	}

	/** full constructor */
	public SysField(BigDecimal fieldId, String fieldCode,
			BigDecimal fieldEntityId, String fieldName,
			String fieldChinaName, String fieldIsNull, String fieldIsKey,
			String fieldType, String fieldLen, String fieldDict,
			BigDecimal fieldSort, String fieldState, String fieldIsMark,
			String operatorName, Timestamp operatorTime, String approveName,
			Timestamp approveTime, String deleteMark, String deleteMarkBack) {
		this.fieldId = fieldId;
		this.fieldCode = fieldCode;
		this.fieldEntityId = fieldEntityId;
		this.fieldName = fieldName;
		this.fieldChinaName = fieldChinaName;
		this.fieldIsNull = fieldIsNull;
		this.fieldIsKey = fieldIsKey;
		this.fieldType = fieldType;
		this.fieldLen = fieldLen;
		this.fieldDict = fieldDict;
		this.fieldSort = fieldSort;
		this.fieldState = fieldState;
		this.fieldIsMark = fieldIsMark;
		this.operatorName = operatorName;
		this.operatorTime = operatorTime;
		this.approveName = approveName;
		this.approveTime = approveTime;
		this.deleteMark = deleteMark;
		this.deleteMarkBack = deleteMarkBack;
	}

	// Property accessors
	@Id
	@Column(name = "FIELD_ID", unique = true, nullable = false, precision = 20, scale = 0)
	public BigDecimal getFieldId() {
		return this.fieldId;
	}

	public void setFieldId(BigDecimal fieldId) {
		this.fieldId = fieldId;
	}

	@Column(name = "FIELD_CODE", length = 50)
	public String getFieldCode() {
		return this.fieldCode;
	}

	public void setFieldCode(String fieldCode) {
		this.fieldCode = fieldCode;
	}

	@Column(name = "FIELD_ENTITY_ID", precision = 20, scale = 0)
	public BigDecimal getFieldEntityId() {
		return this.fieldEntityId;
	}

	public void setFieldEntityId(BigDecimal fieldEntityId) {
		this.fieldEntityId = fieldEntityId;
	}

	@Column(name = "FIELD_NAME", length = 100)
	public String getFieldName() {
		return this.fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	@Column(name = "FIELD_CHINA_NAME", length = 100)
	public String getFieldChinaName() {
		return this.fieldChinaName;
	}

	public void setFieldChinaName(String fieldChinaName) {
		this.fieldChinaName = fieldChinaName;
	}

	@Column(name = "FIELD_IS_NULL", length = 20)
	public String getFieldIsNull() {
		return this.fieldIsNull;
	}

	public void setFieldIsNull(String fieldIsNull) {
		this.fieldIsNull = fieldIsNull;
	}

	@Column(name = "FIELD_IS_KEY", length = 20)
	public String getFieldIsKey() {
		return this.fieldIsKey;
	}

	public void setFieldIsKey(String fieldIsKey) {
		this.fieldIsKey = fieldIsKey;
	}

	@Column(name = "FIELD_TYPE", length = 30)
	public String getFieldType() {
		return this.fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	@Column(name = "FIELD_LEN", length = 20)
	public String getFieldLen() {
		return this.fieldLen;
	}

	public void setFieldLen(String fieldLen) {
		this.fieldLen = fieldLen;
	}

	@Column(name = "FIELD_DICT", length = 20)
	public String getFieldDict() {
		return this.fieldDict;
	}

	public void setFieldDict(String fieldDict) {
		this.fieldDict = fieldDict;
	}

	@Column(name = "FIELD_SORT", precision = 20, scale = 0)
	public BigDecimal getFieldSort() {
		return this.fieldSort;
	}

	public void setFieldSort(BigDecimal fieldSort) {
		this.fieldSort = fieldSort;
	}

	@Column(name = "FIELD_STATE", length = 20)
	public String getFieldState() {
		return this.fieldState;
	}

	public void setFieldState(String fieldState) {
		this.fieldState = fieldState;
	}

	@Column(name = "FIELD_IS_MARK", length = 20)
	public String getFieldIsMark() {
		return this.fieldIsMark;
	}

	public void setFieldIsMark(String fieldIsMark) {
		this.fieldIsMark = fieldIsMark;
	}

	@Column(name = "OPERATOR_NAME", length = 20)
	public String getOperatorName() {
		return this.operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	@Column(name = "OPERATOR_TIME", length = 19)
	public Timestamp getOperatorTime() {
		return this.operatorTime;
	}

	public void setOperatorTime(Timestamp operatorTime) {
		this.operatorTime = operatorTime;
	}

	@Column(name = "APPROVE_NAME", length = 20)
	public String getApproveName() {
		return this.approveName;
	}

	public void setApproveName(String approveName) {
		this.approveName = approveName;
	}

	@Column(name = "APPROVE_TIME", length = 19)
	public Timestamp getApproveTime() {
		return this.approveTime;
	}

	public void setApproveTime(Timestamp approveTime) {
		this.approveTime = approveTime;
	}

	@Column(name = "DELETE_MARK", length = 1)
	public String getDeleteMark() {
		return this.deleteMark;
	}

	public void setDeleteMark(String deleteMark) {
		this.deleteMark = deleteMark;
	}

	@Column(name = "DELETE_MARK_BACK", length = 1)
	public String getDeleteMarkBack() {
		return this.deleteMarkBack;
	}

	public void setDeleteMarkBack(String deleteMarkBack) {
		this.deleteMarkBack = deleteMarkBack;
	}

}