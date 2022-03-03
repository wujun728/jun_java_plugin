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
 * SystemInfo entity. @author Wujun
 */
@Entity
@Table(name = "SYS_SYSTEM_INFO")
@DynamicUpdate(true)
@DynamicInsert(true)
public class SystemInfo implements java.io.Serializable
{
	private static final long serialVersionUID = 7630740517349817047L;
	private Integer systemId;
	private String name;
	private String version;
	private String code;
	private String licensed;
	private String description;
	private String status;
	private Date created;
	private Date lastmod;
	private Integer creater;
	private Integer modifyer;

	// Constructors

	/** default constructor */
	public SystemInfo()
	{
	}

	/** full constructor */
	public SystemInfo(String name, String version, String code, String licensed,
			String description, String status, Date created, Date lastmod, Integer creater,
			Integer modifyer)
	{
		this.name = name;
		this.version = version;
		this.code = code;
		this.licensed = licensed;
		this.description = description;
		this.status = status;
		this.created = created;
		this.lastmod = lastmod;
		this.creater = creater;
		this.modifyer = modifyer;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "SYSTEM_ID", unique = true, nullable = false)
	public Integer getSystemId()
	{
		return this.systemId;
	}

	public void setSystemId(Integer systemId )
	{
		this.systemId = systemId;
	}

	@Column(name = "NAME", length = 50)
	public String getName()
	{
		return this.name;
	}

	public void setName(String name )
	{
		this.name = name;
	}

	@Column(name = "VERSION", length = 50)
	public String getVersion()
	{
		return this.version;
	}

	public void setVersion(String version )
	{
		this.version = version;
	}

	@Column(name = "CODE", length = 50)
	public String getCode()
	{
		return this.code;
	}

	public void setCode(String code )
	{
		this.code = code;
	}

	@Column(name = "LICENSED", length = 50)
	public String getLicensed()
	{
		return this.licensed;
	}

	public void setLicensed(String licensed )
	{
		this.licensed = licensed;
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

	@Column(name = "STATUS", length = 1)
	public String getStatus()
	{
		return this.status;
	}

	public void setStatus(String status )
	{
		this.status = status;
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