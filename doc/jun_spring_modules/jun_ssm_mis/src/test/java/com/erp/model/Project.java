package com.erp.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * Project entity. @author Wujun
 */
@Entity
@Table(name = "SYS_PROJECT")
//@Table(name = "PROJECT", catalog = "ERP")
@DynamicUpdate(true)
@DynamicInsert(true)
public class Project implements java.io.Serializable
{
	private static final long serialVersionUID = -2437115340735034515L;
	private Integer projectId;
	private String myid;
	private String name;
	private Date setupDate;
	private Integer classId;
	private String className;
	private Integer customerId;
	private String customerMyid;
	private String telNo;
	private String contacts;
	private String mobile;
	private Integer sourceId;
	private String sourceName;
	private String budget;
	private Date startTime;
	private Date endTime;
	private String description;
	private Integer attachmentId;
	private String shared;
	private Integer printCount;
	private Integer managerId;
	private String managerName;
	private Integer managerOrganizationId;
	private String managerOrganizationName;
	private Date created;
	private Date lastmod;
	private String status;
	private Integer creater;
	private Integer modifyer;

	// Constructors

	/** default constructor */
	public Project()
	{
	}

	/** full constructor */
	public Project(String myid, String name, Date setupDate, Integer classId, String className,
			Integer customerId, String customerMyid, String telNo, String contacts, String mobile,
			Integer sourceId, String sourceName, String budget, Date startTime, Date endTime,
			String description, Integer attachmentId, String shared, Integer printCount,
			Integer managerId, String managerName, Integer managerOrganizationId,
			String managerOrganizationName, Date created, Date lastmod, String status,
			Integer creater, Integer modifyer)
	{
		this.myid = myid;
		this.name = name;
		this.setupDate = setupDate;
		this.classId = classId;
		this.className = className;
		this.customerId = customerId;
		this.customerMyid = customerMyid;
		this.telNo = telNo;
		this.contacts = contacts;
		this.mobile = mobile;
		this.sourceId = sourceId;
		this.sourceName = sourceName;
		this.budget = budget;
		this.startTime = startTime;
		this.endTime = endTime;
		this.description = description;
		this.attachmentId = attachmentId;
		this.shared = shared;
		this.printCount = printCount;
		this.managerId = managerId;
		this.managerName = managerName;
		this.managerOrganizationId = managerOrganizationId;
		this.managerOrganizationName = managerOrganizationName;
		this.created = created;
		this.lastmod = lastmod;
		this.status = status;
		this.creater = creater;
		this.modifyer = modifyer;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "PROJECT_ID", unique = true, nullable = false)
	public Integer getProjectId()
	{
		return this.projectId;
	}

	public void setProjectId(Integer projectId )
	{
		this.projectId = projectId;
	}

	@Column(name = "MYID", length = 55)
	public String getMyid()
	{
		return this.myid;
	}

	public void setMyid(String myid )
	{
		this.myid = myid;
	}

	@Column(name = "NAME", length = 200)
	public String getName()
	{
		return this.name;
	}

	public void setName(String name )
	{
		this.name = name;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "SETUP_DATE", length = 10)
	public Date getSetupDate()
	{
		return this.setupDate;
	}

	public void setSetupDate(Date setupDate )
	{
		this.setupDate = setupDate;
	}

	@Column(name = "CLASS_ID")
	public Integer getClassId()
	{
		return this.classId;
	}

	public void setClassId(Integer classId )
	{
		this.classId = classId;
	}

	@Column(name = "CLASS_NAME", length = 200)
	public String getClassName()
	{
		return this.className;
	}

	public void setClassName(String className )
	{
		this.className = className;
	}

	@Column(name = "CUSTOMER_ID")
	public Integer getCustomerId()
	{
		return this.customerId;
	}

	public void setCustomerId(Integer customerId )
	{
		this.customerId = customerId;
	}

	@Column(name = "CUSTOMER_MYID", length = 55)
	public String getCustomerMyid()
	{
		return this.customerMyid;
	}

	public void setCustomerMyid(String customerMyid )
	{
		this.customerMyid = customerMyid;
	}

	@Column(name = "TEL_NO", length = 20)
	public String getTelNo()
	{
		return this.telNo;
	}

	public void setTelNo(String telNo )
	{
		this.telNo = telNo;
	}

	@Column(name = "CONTACTS", length = 20)
	public String getContacts()
	{
		return this.contacts;
	}

	public void setContacts(String contacts )
	{
		this.contacts = contacts;
	}

	@Column(name = "MOBILE", length = 20)
	public String getMobile()
	{
		return this.mobile;
	}

	public void setMobile(String mobile )
	{
		this.mobile = mobile;
	}

	@Column(name = "SOURCE_ID")
	public Integer getSourceId()
	{
		return this.sourceId;
	}

	public void setSourceId(Integer sourceId )
	{
		this.sourceId = sourceId;
	}

	@Column(name = "SOURCE_NAME", length = 200)
	public String getSourceName()
	{
		return this.sourceName;
	}

	public void setSourceName(String sourceName )
	{
		this.sourceName = sourceName;
	}

	@Column(name = "BUDGET", length = 55)
	public String getBudget()
	{
		return this.budget;
	}

	public void setBudget(String budget )
	{
		this.budget = budget;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "START_TIME", length = 10)
	public Date getStartTime()
	{
		return this.startTime;
	}

	public void setStartTime(Date startTime )
	{
		this.startTime = startTime;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "END_TIME", length = 10)
	public Date getEndTime()
	{
		return this.endTime;
	}

	public void setEndTime(Date endTime )
	{
		this.endTime = endTime;
	}

	@Column(name = "DESCRIPTION", length = 2000)
	public String getDescription()
	{
		return this.description;
	}

	public void setDescription(String description )
	{
		this.description = description;
	}

	@Column(name = "ATTACHMENT_ID")
	public Integer getAttachmentId()
	{
		return this.attachmentId;
	}

	public void setAttachmentId(Integer attachmentId )
	{
		this.attachmentId = attachmentId;
	}

	@Column(name = "SHARED", length = 200)
	public String getShared()
	{
		return this.shared;
	}

	public void setShared(String shared )
	{
		this.shared = shared;
	}

	@Column(name = "PRINT_COUNT")
	public Integer getPrintCount()
	{
		return this.printCount;
	}

	public void setPrintCount(Integer printCount )
	{
		this.printCount = printCount;
	}

	@Column(name = "MANAGER_ID")
	public Integer getManagerId()
	{
		return this.managerId;
	}

	public void setManagerId(Integer managerId )
	{
		this.managerId = managerId;
	}

	@Column(name = "MANAGER_NAME", length = 55)
	public String getManagerName()
	{
		return this.managerName;
	}

	public void setManagerName(String managerName )
	{
		this.managerName = managerName;
	}

	@Column(name = "MANAGER_ORGANIZATION_ID")
	public Integer getManagerOrganizationId()
	{
		return this.managerOrganizationId;
	}

	public void setManagerOrganizationId(Integer managerOrganizationId )
	{
		this.managerOrganizationId = managerOrganizationId;
	}

	@Column(name = "MANAGER_ORGANIZATION_NAME", length = 55)
	public String getManagerOrganizationName()
	{
		return this.managerOrganizationName;
	}

	public void setManagerOrganizationName(String managerOrganizationName )
	{
		this.managerOrganizationName = managerOrganizationName;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATED", length = 10)
	public Date getCreated()
	{
		return this.created;
	}

	public void setCreated(Date created )
	{
		this.created = created;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "LASTMOD", length = 10)
	public Date getLastmod()
	{
		return this.lastmod;
	}

	public void setLastmod(Date lastmod )
	{
		this.lastmod = lastmod;
	}

	@Column(name = "STATUS", length = 1)
	public String getStatus()
	{
		return this.status;
	}

	public void setStatus(String status )
	{
		this.status = status;
	}

	@Column(name = "CREATER")
	public Integer getCreater()
	{
		return this.creater;
	}

	public void setCreater(Integer creater )
	{
		this.creater = creater;
	}

	@Column(name = "MODIFYER")
	public Integer getModifyer()
	{
		return this.modifyer;
	}

	public void setModifyer(Integer modifyer )
	{
		this.modifyer = modifyer;
	}

}