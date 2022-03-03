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
 * Bug entity. @author Wujun
 */
@Entity
@Table(name = "SYS_BUG")
@DynamicUpdate(true)
@DynamicInsert(true)
public class Bug implements java.io.Serializable
{
	private static final long serialVersionUID = 4645775188016310313L;
	private Integer bugId;
	private String bugName;
	private String status;
	private String attachmentUrl;
	private String description;
	private Date created;
	private Date lastmod;
	private Integer creater;
	private Integer modifyer;

	// Constructors

	/** default constructor */
	public Bug()
	{
	}

	/** full constructor */
	public Bug(String bugName, String description, Date created, Date lastmod, Integer creater,
			Integer modifyer)
	{
		this.bugName = bugName;
		this.description = description;
		this.created = created;
		this.lastmod = lastmod;
		this.creater = creater;
		this.modifyer = modifyer;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "BUG_ID", unique = true, nullable = false)
	public Integer getBugId()
	{
		return this.bugId;
	}

	public void setBugId(Integer bugId )
	{
		this.bugId = bugId;
	}

	@Column(name = "BUG_NAME", length = 300)
	public String getBugName()
	{
		return this.bugName;
	}

	public void setBugName(String bugName )
	{
		this.bugName = bugName;
	}
	
	@Column(name = "ATTACHMENT_URL", length = 300)
	public String getAttachmentUrl()
	{
		return attachmentUrl;
	}

	public void setAttachmentUrl(String attachmentUrl )
	{
		this.attachmentUrl = attachmentUrl;
	}

	@Column(name = "STATUS", nullable = false, length = 1)
	public String getStatus()
	{
		return this.status;
	}

	public void setStatus(String status )
	{
		this.status = status;
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED", length = 10)
	public Date getCreated()
	{
		return this.created;
	}

	public void setCreated(Date created )
	{
		this.created = created;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LASTMOD", length = 10)
	public Date getLastmod()
	{
		return this.lastmod;
	}

	public void setLastmod(Date lastmod )
	{
		this.lastmod = lastmod;
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