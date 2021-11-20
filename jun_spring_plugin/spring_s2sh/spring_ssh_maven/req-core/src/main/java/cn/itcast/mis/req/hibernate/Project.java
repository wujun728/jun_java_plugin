package cn.itcast.mis.req.hibernate;

import java.io.Serializable;
import java.util.Date;

/** @author Wujun
public class Project implements Serializable {

    /** nullable persistent field */
    private Integer projectId;

    /** nullable persistent field */
    private String projectName;

    /** nullable persistent field */
    private Boolean projectApprovestatus;

    /** nullable persistent field */
    private Double yearreceivedAmount;

    /** nullable persistent field */
    private Boolean projectActivestatus;

    /** nullable persistent field */
    private String projectNumber;

    /** nullable persistent field */
    private java.util.Date signDate;

    /** nullable persistent field */
    private String beforesaleProjectnumber;

    /** nullable persistent field */
    private String contractAbstract;

    /** nullable persistent field */
    private String contactCustomer;

    /** nullable persistent field */
    private String customerTel;

    /** nullable persistent field */
    private String customerEmail;

    /** nullable persistent field */
    private java.util.Date applyDate;

    /** nullable persistent field */
    private java.util.Date passDate;

    /** nullable persistent field */
    private String approveSuggestion;

    /** nullable persistent field */
    private String projectDescription;

    /** nullable persistent field */
    private String projectClause;

    /** nullable persistent field */
    private Integer maintainPeriod;

    /** nullable persistent field */
    private java.lang.Boolean projectType;

    /** nullable persistent field */
    private Integer priority;

    /** nullable persistent field */
    private Integer customerId;

    /** nullable persistent field */
    private Integer userId;

    /** nullable persistent field */
    private Integer parentId;
    
    private Integer deliverGoodsFlag;

    private Integer projectDept;
    
    private Date abstractReceivedDate;
    private Date deliverReceivedDate;
    private Date finishReceivedDate;
    private Date acceptReceivedDate;

    private Integer selfDoneFlag; //�����Բ���ɱ�־
    
    public Project(Integer projectId, String projectName,
			Boolean projectApprovestatus, Double yearreceivedAmount,
			Boolean projectActivestatus, String projectNumber, Date signDate,
			String beforesaleProjectnumber, String contractAbstract,
			String contactCustomer, String customerTel, String customerEmail,
			Date applyDate, Date passDate, String approveSuggestion,
			String projectDescription, String projectClause,
			Integer maintainPeriod, java.lang.Boolean projectType, Integer priority,
			Integer customerId, Integer userId, Integer parentId,
			Integer deliverGoodsFlag, Integer projectDept) {
		super();
		this.projectId = projectId;
		this.projectName = projectName;
		this.projectApprovestatus = projectApprovestatus;
		this.yearreceivedAmount = yearreceivedAmount;
		this.projectActivestatus = projectActivestatus;
		this.projectNumber = projectNumber;
		this.signDate = signDate;
		this.beforesaleProjectnumber = beforesaleProjectnumber;
		this.contractAbstract = contractAbstract;
		this.contactCustomer = contactCustomer;
		this.customerTel = customerTel;
		this.customerEmail = customerEmail;
		this.applyDate = applyDate;
		this.passDate = passDate;
		this.approveSuggestion = approveSuggestion;
		this.projectDescription = projectDescription;
		this.projectClause = projectClause;
		this.maintainPeriod = maintainPeriod;
		this.projectType = projectType;
		this.priority = priority;
		this.customerId = customerId;
		this.userId = userId;
		this.parentId = parentId;
		this.deliverGoodsFlag = deliverGoodsFlag;
		this.projectDept = projectDept;
	}

	public Project(Integer projectId, String projectName,
			Boolean projectApprovestatus, Double yearreceivedAmount,
			Boolean projectActivestatus, String projectNumber, Date signDate,
			String beforesaleProjectnumber, String contractAbstract,
			String contactCustomer, String customerTel, String customerEmail,
			Date applyDate, Date passDate, String approveSuggestion,
			String projectDescription, String projectClause,
			Integer maintainPeriod, java.lang.Boolean projectType, Integer priority,
			Integer customerId, Integer userId, Integer parentId,
			Integer deliverGoodsFlag) {
		super();
		this.projectId = projectId;
		this.projectName = projectName;
		this.projectApprovestatus = projectApprovestatus;
		this.yearreceivedAmount = yearreceivedAmount;
		this.projectActivestatus = projectActivestatus;
		this.projectNumber = projectNumber;
		this.signDate = signDate;
		this.beforesaleProjectnumber = beforesaleProjectnumber;
		this.contractAbstract = contractAbstract;
		this.contactCustomer = contactCustomer;
		this.customerTel = customerTel;
		this.customerEmail = customerEmail;
		this.applyDate = applyDate;
		this.passDate = passDate;
		this.approveSuggestion = approveSuggestion;
		this.projectDescription = projectDescription;
		this.projectClause = projectClause;
		this.maintainPeriod = maintainPeriod;
		this.projectType = projectType;
		this.priority = priority;
		this.customerId = customerId;
		this.userId = userId;
		this.parentId = parentId;
		this.deliverGoodsFlag = deliverGoodsFlag;
	}

	public Project(Integer projectId, String projectName,
			Boolean projectApprovestatus, Double yearreceivedAmount,
			Boolean projectActivestatus, String projectNumber, Date signDate,
			String beforesaleProjectnumber, String contractAbstract,
			String contactCustomer, String customerTel, String customerEmail,
			Date applyDate, Date passDate, String approveSuggestion,
			String projectDescription, String projectClause,
			Integer maintainPeriod, Boolean projectType, Integer priority,
			Integer customerId, Integer userId, Integer parentId,
			Integer deliverGoodsFlag, Integer projectDept,
			Date abstractReceivedDate, Date deliverReceivedDate,
			Date finishReceivedDate, Date acceptReceivedDate) {
		super();
		this.projectId = projectId;
		this.projectName = projectName;
		this.projectApprovestatus = projectApprovestatus;
		this.yearreceivedAmount = yearreceivedAmount;
		this.projectActivestatus = projectActivestatus;
		this.projectNumber = projectNumber;
		this.signDate = signDate;
		this.beforesaleProjectnumber = beforesaleProjectnumber;
		this.contractAbstract = contractAbstract;
		this.contactCustomer = contactCustomer;
		this.customerTel = customerTel;
		this.customerEmail = customerEmail;
		this.applyDate = applyDate;
		this.passDate = passDate;
		this.approveSuggestion = approveSuggestion;
		this.projectDescription = projectDescription;
		this.projectClause = projectClause;
		this.maintainPeriod = maintainPeriod;
		this.projectType = projectType;
		this.priority = priority;
		this.customerId = customerId;
		this.userId = userId;
		this.parentId = parentId;
		this.deliverGoodsFlag = deliverGoodsFlag;
		this.projectDept = projectDept;
		this.abstractReceivedDate = abstractReceivedDate;
		this.deliverReceivedDate = deliverReceivedDate;
		this.finishReceivedDate = finishReceivedDate;
		this.acceptReceivedDate = acceptReceivedDate;
	}

	/** default constructor */
    public Project() {
    }

    public java.lang.Integer getProjectId() {
        return this.projectId;
    }

    public void setProjectId(java.lang.Integer projectId) {
        this.projectId = projectId;
    }

    public java.lang.String getProjectName() {
        return this.projectName;
    }

    public void setProjectName(java.lang.String projectName) {
        this.projectName = projectName;
    }

    public java.lang.Boolean getProjectApprovestatus() {
        return this.projectApprovestatus;
    }

    public void setProjectApprovestatus(java.lang.Boolean projectApprovestatus) {
        this.projectApprovestatus = projectApprovestatus;
    }

    public java.lang.Double getYearreceivedAmount() {
        return this.yearreceivedAmount;
    }

    public void setYearreceivedAmount(java.lang.Double yearreceivedAmount) {
        this.yearreceivedAmount = yearreceivedAmount;
    }

    public java.lang.Boolean getProjectActivestatus() {
        return this.projectActivestatus;
    }

    public void setProjectActivestatus(java.lang.Boolean projectActivestatus) {
        this.projectActivestatus = projectActivestatus;
    }

    public java.lang.String getProjectNumber() {
        return this.projectNumber;
    }

    public void setProjectNumber(java.lang.String projectNumber) {
        this.projectNumber = projectNumber;
    }

    public java.util.Date getSignDate() {
        return this.signDate;
    }

    public void setSignDate(java.util.Date signDate) {
        this.signDate = signDate;
    }

    public java.lang.String getBeforesaleProjectnumber() {
        return this.beforesaleProjectnumber;
    }

    public void setBeforesaleProjectnumber(java.lang.String beforesaleProjectnumber) {
        this.beforesaleProjectnumber = beforesaleProjectnumber;
    }

    public java.lang.String getContractAbstract() {
        return this.contractAbstract;
    }

    public void setContractAbstract(java.lang.String contractAbstract) {
        this.contractAbstract = contractAbstract;
    }

    public java.lang.String getContactCustomer() {
        return this.contactCustomer;
    }

    public void setContactCustomer(java.lang.String contactCustomer) {
        this.contactCustomer = contactCustomer;
    }

    public java.lang.String getCustomerTel() {
        return this.customerTel;
    }

    public void setCustomerTel(java.lang.String customerTel) {
        this.customerTel = customerTel;
    }

    public java.lang.String getCustomerEmail() {
        return this.customerEmail;
    }

    public void setCustomerEmail(java.lang.String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public java.util.Date getApplyDate() {
        return this.applyDate;
    }

    public void setApplyDate(java.util.Date applyDate) {
        this.applyDate = applyDate;
    }

    public java.util.Date getPassDate() {
        return this.passDate;
    }

    public void setPassDate(java.util.Date passDate) {
        this.passDate = passDate;
    }

    public java.lang.String getApproveSuggestion() {
        return this.approveSuggestion;
    }

    public void setApproveSuggestion(java.lang.String approveSuggestion) {
        this.approveSuggestion = approveSuggestion;
    }

    public java.lang.String getProjectDescription() {
        return this.projectDescription;
    }

    public void setProjectDescription(java.lang.String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public java.lang.String getProjectClause() {
        return this.projectClause;
    }

    public void setProjectClause(java.lang.String projectClause) {
        this.projectClause = projectClause;
    }

    public java.lang.Integer getMaintainPeriod() {
        return this.maintainPeriod;
    }

    public void setMaintainPeriod(java.lang.Integer maintainPeriod) {
        this.maintainPeriod = maintainPeriod;
    }

    public java.lang.Boolean getProjectType() {
        return this.projectType;
    }

    public void setProjectType(java.lang.Boolean projectType) {
        this.projectType = projectType;
    }

    public java.lang.Integer getPriority() {
        return this.priority;
    }

    public void setPriority(java.lang.Integer priority) {
        this.priority = priority;
    }

    public java.lang.Integer getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(java.lang.Integer customerId) {
        this.customerId = customerId;
    }

    public java.lang.Integer getUserId() {
        return this.userId;
    }

    public void setUserId(java.lang.Integer userId) {
        this.userId = userId;
    }

    public java.lang.Integer getParentId() {
        return this.parentId;
    }

    public void setParentId(java.lang.Integer parentId) {
        this.parentId = parentId;
    }


	public Integer getDeliverGoodsFlag() {
		return deliverGoodsFlag;
	}

	public void setDeliverGoodsFlag(Integer deliverGoodsFlag) {
		this.deliverGoodsFlag = deliverGoodsFlag;
	}

	public Integer getProjectDept() {
		return projectDept;
	}

	public void setProjectDept(Integer projectDept) {
		this.projectDept = projectDept;
	}

	public Date getAbstractReceivedDate() {
		return abstractReceivedDate;
	}

	public void setAbstractReceivedDate(Date abstractReceivedDate) {
		this.abstractReceivedDate = abstractReceivedDate;
	}

	public Date getDeliverReceivedDate() {
		return deliverReceivedDate;
	}

	public void setDeliverReceivedDate(Date deliverReceivedDate) {
		this.deliverReceivedDate = deliverReceivedDate;
	}

	public Date getFinishReceivedDate() {
		return finishReceivedDate;
	}

	public void setFinishReceivedDate(Date finishReceivedDate) {
		this.finishReceivedDate = finishReceivedDate;
	}

	public Date getAcceptReceivedDate() {
		return acceptReceivedDate;
	}

	public void setAcceptReceivedDate(Date acceptReceivedDate) {
		this.acceptReceivedDate = acceptReceivedDate;
	}

	public Integer getSelfDoneFlag() {
		return selfDoneFlag;
	}

	public void setSelfDoneFlag(Integer selfDoneFlag) {
		this.selfDoneFlag = selfDoneFlag;
	}

}
