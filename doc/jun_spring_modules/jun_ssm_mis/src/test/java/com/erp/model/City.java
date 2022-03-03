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
 * City entity. @author Wujun
 */
@Entity
@Table(name = "SYS_CITY")
@DynamicUpdate(true)
@DynamicInsert(true)
public class City implements java.io.Serializable
{
	private static final long serialVersionUID = 2605382447659254392L;
	private Integer cityId;
	private Integer provinceId;
	private String name;
	private Date created;
	private Date lastmod;
	private String status;
	private Integer creater;
	private Integer modifyer;

	// Constructors

	/** default constructor */
	public City()
	{
	}

	/** minimal constructor */
	public City(Integer provinceId, String name, Date created, String status)
	{
		this.provinceId = provinceId;
		this.name = name;
		this.created = created;
		this.status = status;
	}

	/** full constructor */
	public City(Integer provinceId, String name, Date created, Date lastmod, String status,
			Integer creater, Integer modifyer)
	{
		this.provinceId = provinceId;
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
	@Column(name = "CITY_ID", unique = true, nullable = false)
	public Integer getCityId()
	{
		return this.cityId;
	}

	public void setCityId(Integer cityId )
	{
		this.cityId = cityId;
	}

	@Column(name = "PROVINCE_ID", nullable = false)
	public Integer getProvinceId()
	{
		return this.provinceId;
	}

	public void setProvinceId(Integer provinceId )
	{
		this.provinceId = provinceId;
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