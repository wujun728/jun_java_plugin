package com.erp.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SysEntity entity. @author Wujun
 */
@Entity
@Table(name = "sys_entity")
public class SysEntity implements java.io.Serializable {

	// Fields

	private BigDecimal entityId;
	private String entityCode;
	private String entityTablename;
	private String entityType;
	private String entityChinaname;
	private BigDecimal entitySort;
	private String entityDes;
	private String entityState;
	private String entityMark;
	private String operatorName;
	private Timestamp operatorTime;
	private String approveName;
	private Timestamp approveTime;
	private String deleteMark;
	private String dsname;

	// Constructors

	/** default constructor */
	public SysEntity() {
	}

	/** minimal constructor */
	public SysEntity(BigDecimal entityId) {
		this.entityId = entityId;
	}

	/** full constructor */
	public SysEntity(BigDecimal entityId, String entityCode,
			String entityTablename, String entityType, String entityChinaname,
			BigDecimal entitySort, String entityDes, String entityState,
			String entityMark, String operatorName, Timestamp operatorTime,
			String approveName, Timestamp approveTime, String deleteMark,
			String dsname) {
		this.entityId = entityId;
		this.entityCode = entityCode;
		this.entityTablename = entityTablename;
		this.entityType = entityType;
		this.entityChinaname = entityChinaname;
		this.entitySort = entitySort;
		this.entityDes = entityDes;
		this.entityState = entityState;
		this.entityMark = entityMark;
		this.operatorName = operatorName;
		this.operatorTime = operatorTime;
		this.approveName = approveName;
		this.approveTime = approveTime;
		this.deleteMark = deleteMark;
		this.dsname = dsname;
	}

	// Property accessors
	@Id
	@Column(name = "ENTITY_ID", unique = true, nullable = false, precision = 20, scale = 0)
	public BigDecimal getEntityId() {
		return this.entityId;
	}

	public void setEntityId(BigDecimal entityId) {
		this.entityId = entityId;
	}

	@Column(name = "ENTITY_CODE", length = 50)
	public String getEntityCode() {
		return this.entityCode;
	}

	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}

	@Column(name = "ENTITY_TABLENAME", length = 100)
	public String getEntityTablename() {
		return this.entityTablename;
	}

	public void setEntityTablename(String entityTablename) {
		this.entityTablename = entityTablename;
	}

	@Column(name = "ENTITY_TYPE", length = 20)
	public String getEntityType() {
		return this.entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	@Column(name = "ENTITY_CHINANAME", length = 100)
	public String getEntityChinaname() {
		return this.entityChinaname;
	}

	public void setEntityChinaname(String entityChinaname) {
		this.entityChinaname = entityChinaname;
	}

	@Column(name = "ENTITY_SORT", precision = 20, scale = 0)
	public BigDecimal getEntitySort() {
		return this.entitySort;
	}

	public void setEntitySort(BigDecimal entitySort) {
		this.entitySort = entitySort;
	}

	@Column(name = "ENTITY_DES", length = 65535)
	public String getEntityDes() {
		return this.entityDes;
	}

	public void setEntityDes(String entityDes) {
		this.entityDes = entityDes;
	}

	@Column(name = "ENTITY_STATE", length = 20)
	public String getEntityState() {
		return this.entityState;
	}

	public void setEntityState(String entityState) {
		this.entityState = entityState;
	}

	@Column(name = "ENTITY_MARK", length = 20)
	public String getEntityMark() {
		return this.entityMark;
	}

	public void setEntityMark(String entityMark) {
		this.entityMark = entityMark;
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

	@Column(name = "DSNAME", length = 64)
	public String getDsname() {
		return this.dsname;
	}

	public void setDsname(String dsname) {
		this.dsname = dsname;
	}

}