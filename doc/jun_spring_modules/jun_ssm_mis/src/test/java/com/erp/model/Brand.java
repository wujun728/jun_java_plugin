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
 * Brand entity. @author Wujun
 */
@Entity
@Table(name = "SYS_BRAND")
@DynamicUpdate(true)
@DynamicInsert(true)
public class Brand implements java.io.Serializable
{
	private static final long serialVersionUID = 7957634034245022016L;
	private Integer brandId;
	private String name;
	private Date created;
	private Date lastmod;
	private String status;
	private Integer creater;
	private Integer modifyer;

	// Constructors

	/** default constructor */
	public Brand()
	{
	}
	/** full constructor */
	public Brand(String name, Date created, Date lastmod, String status, Integer creater,
			Integer modifyer)
	{
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
	@Column(name = "BRAND_ID", unique = true, nullable = false)
	public Integer getBrandId()
	{
		return this.brandId;
	}

	public void setBrandId(Integer brandId )
	{
		this.brandId = brandId;
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