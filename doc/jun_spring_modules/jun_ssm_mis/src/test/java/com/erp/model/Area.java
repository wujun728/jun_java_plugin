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
 * Area entity. @author Wujun
 */
@Entity
@Table(name = "SYS_AREA")
@DynamicUpdate(true)
@DynamicInsert(true)
public class Area implements java.io.Serializable
{
	private static final long serialVersionUID = -2488468164422496390L;
	private Integer areaId;
	private String name;
	private Date created;
	private Date lastmod;
	private String status;
	private Integer creater;
	private Integer modifyer;
	private Integer pid;

	// Constructors

	/** default constructor */
	public Area()
	{
	}

	/** minimal constructor */
	public Area(String name, Date created, String status)
	{
		this.name = name;
		this.created = created;
		this.status = status;
	}

	/** full constructor */
	public Area(String name, Date created, Date lastmod, String status, Integer creater,
			Integer modifyer, Integer pid)
	{
		this.name = name;
		this.created = created;
		this.lastmod = lastmod;
		this.status = status;
		this.creater = creater;
		this.modifyer = modifyer;
		this.pid = pid;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "AREA_ID", unique = true, nullable = false)
	public Integer getAreaId()
	{
		return this.areaId;
	}

	public void setAreaId(Integer areaId )
	{
		this.areaId = areaId;
	}

	@Column(name = "NAME", nullable = false, length = 200)
	public String getName()
	{
		return this.name;
	}

	public void setName(String name )
	{
		this.name = name;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATED", nullable = false, length = 10)
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

	@Column(name = "STATUS", nullable = false, length = 1)
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

	@Column(name = "PID")
	public Integer getPid()
	{
		return this.pid;
	}

	public void setPid(Integer pid )
	{
		this.pid = pid;
	}

}