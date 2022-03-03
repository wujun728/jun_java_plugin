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
 * ProjectFollow entity. @author Wujun
 */
@Entity
@Table(name = "SYS_PROJECT_FOLLOW")
@DynamicUpdate(true)
@DynamicInsert(true)
public class ProjectFollow implements java.io.Serializable
{
	private static final long serialVersionUID = 8844614258030631022L;
	private Integer followId;
	private Integer projectId;
	private String projectName;
	private Integer customerId;
	private String customerName;
	private Date contcatDate;
	private String contcat;
	private Integer followClass;
	private String className;
	private Integer followStatus;
	private String statusName;
	private Integer progress;
	private String content;
	private String description;
	private Integer printCount;
	private Integer enterId;
	private Date created;
	private Date lastmod;
	private String status;
	private Integer creater;
	private Integer modifyer;
	private String enterName;
	private Date enterDate;
	private Integer enterOrganizationId;
	private String enterOrganizationName;

	// Constructors

	/** default constructor */
	public ProjectFollow()
	{
	}

	/** full constructor */
	public ProjectFollow(Integer projectId, String projectName, Integer customerId,
			String customerName, Date contcatDate, String contcat, Integer followClass,
			String className, Integer followStatus, String statusName, Integer progress,
			String content, String description, Integer printCount, Integer enterId, Date created,
			Date lastmod, String status, Integer creater, Integer modifyer, String enterName,
			Date enterDate, Integer enterOrganizationId, String enterOrganizationName)
	{
		this.projectId = projectId;
		this.projectName = projectName;
		this.customerId = customerId;
		this.customerName = customerName;
		this.contcatDate = contcatDate;
		this.contcat = contcat;
		this.followClass = followClass;
		this.className = className;
		this.followStatus = followStatus;
		this.statusName = statusName;
		this.progress = progress;
		this.content = content;
		this.description = description;
		this.printCount = printCount;
		this.enterId = enterId;
		this.created = created;
		this.lastmod = lastmod;
		this.status = status;
		this.creater = creater;
		this.modifyer = modifyer;
		this.enterName = enterName;
		this.enterDate = enterDate;
		this.enterOrganizationId = enterOrganizationId;
		this.enterOrganizationName = enterOrganizationName;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "FOLLOW_ID", unique = true, nullable = false)
	public Integer getFollowId()
	{
		return this.followId;
	}

	public void setFollowId(Integer followId )
	{
		this.followId = followId;
	}

	@Column(name = "PROJECT_ID")
	public Integer getProjectId()
	{
		return this.projectId;
	}

	public void setProjectId(Integer projectId )
	{
		this.projectId = projectId;
	}

	@Column(name = "PROJECT_NAME", length = 100)
	public String getProjectName()
	{
		return this.projectName;
	}

	public void setProjectName(String projectName )
	{
		this.projectName = projectName;
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

	@Column(name = "CUSTOMER_NAME", length = 200)
	public String getCustomerName()
	{
		return this.customerName;
	}

	public void setCustomerName(String customerName )
	{
		this.customerName = customerName;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CONTCAT_DATE", length = 10)
	public Date getContcatDate()
	{
		return this.contcatDate;
	}

	public void setContcatDate(Date contcatDate )
	{
		this.contcatDate = contcatDate;
	}

	@Column(name = "CONTCAT", length = 55)
	public String getContcat()
	{
		return this.contcat;
	}

	public void setContcat(String contcat )
	{
		this.contcat = contcat;
	}

	@Column(name = "FOLLOW_CLASS")
	public Integer getFollowClass()
	{
		return this.followClass;
	}

	public void setFollowClass(Integer followClass )
	{
		this.followClass = followClass;
	}

	@Column(name = "CLASS_NAME", length = 55)
	public String getClassName()
	{
		return this.className;
	}

	public void setClassName(String className )
	{
		this.className = className;
	}

	@Column(name = "FOLLOW_STATUS")
	public Integer getFollowStatus()
	{
		return this.followStatus;
	}

	public void setFollowStatus(Integer followStatus )
	{
		this.followStatus = followStatus;
	}

	@Column(name = "STATUS_NAME", length = 55)
	public String getStatusName()
	{
		return this.statusName;
	}

	public void setStatusName(String statusName )
	{
		this.statusName = statusName;
	}

	@Column(name = "PROGRESS")
	public Integer getProgress()
	{
		return this.progress;
	}

	public void setProgress(Integer progress )
	{
		this.progress = progress;
	}

	@Column(name = "CONTENT", length = 2000)
	public String getContent()
	{
		return this.content;
	}

	public void setContent(String content )
	{
		this.content = content;
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

	@Column(name = "PRINT_COUNT")
	public Integer getPrintCount()
	{
		return this.printCount;
	}

	public void setPrintCount(Integer printCount )
	{
		this.printCount = printCount;
	}

	@Column(name = "ENTER_ID")
	public Integer getEnterId()
	{
		return this.enterId;
	}

	public void setEnterId(Integer enterId )
	{
		this.enterId = enterId;
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

	@Column(name = "ENTER_NAME", length = 55)
	public String getEnterName()
	{
		return this.enterName;
	}

	public void setEnterName(String enterName )
	{
		this.enterName = enterName;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "ENTER_DATE", length = 10)
	public Date getEnterDate()
	{
		return this.enterDate;
	}

	public void setEnterDate(Date enterDate )
	{
		this.enterDate = enterDate;
	}

	@Column(name = "ENTER_ORGANIZATION_ID")
	public Integer getEnterOrganizationId()
	{
		return this.enterOrganizationId;
	}

	public void setEnterOrganizationId(Integer enterOrganizationId )
	{
		this.enterOrganizationId = enterOrganizationId;
	}

	@Column(name = "ENTER_ORGANIZATION_NAME", length = 55)
	public String getEnterOrganizationName()
	{
		return this.enterOrganizationName;
	}

	public void setEnterOrganizationName(String enterOrganizationName )
	{
		this.enterOrganizationName = enterOrganizationName;
	}

}