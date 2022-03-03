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
 * Province entity. @author Wujun
 */
@Entity
@Table(name = "SYS_PROVINCE")
@DynamicUpdate(true)
@DynamicInsert(true)
public class Province implements java.io.Serializable
{
	private static final long serialVersionUID = 6598216154133047433L;
	private Integer provinceId;
	private Integer areaId;
	private String name;
	private Date created;
	private Date lastmod;
	private String status;
	private Integer creater;
	private Integer modifyer;

	// Constructors

	/** default constructor */
	public Province()
	{
	}

	/** minimal constructor */
	public Province(Integer areaId, String name, Date created, String status)
	{
		this.areaId = areaId;
		this.name = name;
		this.created = created;
		this.status = status;
	}

	/** full constructor */
	public Province(Integer areaId, String name, Date created, Date lastmod, String status,
			Integer creater, Integer modifyer)
	{
		this.areaId = areaId;
		this.name = name;
		this.created = created;
		this.lastmod = lastmod;
		this.status = status;
		this.creater = creater;
		this.modifyer = modifyer;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "PROVINCE_ID", unique = true, nullable = false)
	public Integer getProvinceId()
	{
		return this.provinceId;
	}

	public void setProvinceId(Integer provinceId )
	{
		this.provinceId = provinceId;
	}

	@Column(name = "AREA_ID", nullable = false)
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

}