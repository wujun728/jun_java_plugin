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
 * Warehouse entity. @author Wujun
 */
@Entity
@Table(name = "SYS_WAREHOUSE")
@DynamicUpdate(true)
@DynamicInsert(true)
public class Warehouse implements java.io.Serializable
{
	private static final long serialVersionUID = -3125815986421108087L;
	private Integer warehouseId;
	private String name;
	private String myid;
	private Integer organizationId;
	private String organizationName;
	private Integer managerId;
	private String managerName;
	private String tel;
	private String address;
	private String description;
	private Date created;
	private Date lastmod;
	private String status;
	private Integer creater;
	private Integer modifyer;

	// Constructors

	/** default constructor */
	public Warehouse()
	{
	}

	/** full constructor */
	public Warehouse(String name, String myid, Integer organizationId, String organizationName,
			Integer managerId, String managerName, String tel, String address, String description,
			Date created, Date lastmod, String status, Integer creater, Integer modifyer)
	{
		this.name = name;
		this.myid = myid;
		this.organizationId = organizationId;
		this.organizationName = organizationName;
		this.managerId = managerId;
		this.managerName = managerName;
		this.tel = tel;
		this.address = address;
		this.description = description;
		this.created = created;
		this.lastmod = lastmod;
		this.status = status;
		this.creater = creater;
		this.modifyer = modifyer;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "WAREHOUSE_ID", unique = true, nullable = false)
	public Integer getWarehouseId()
	{
		return this.warehouseId;
	}

	public void setWarehouseId(Integer warehouseId )
	{
		this.warehouseId = warehouseId;
	}

	@Column(name = "NAME", length = 55)
	public String getName()
	{
		return this.name;
	}

	public void setName(String name )
	{
		this.name = name;
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

	@Column(name = "ORGANIZATION_ID")
	public Integer getOrganizationId()
	{
		return this.organizationId;
	}

	public void setOrganizationId(Integer organizationId )
	{
		this.organizationId = organizationId;
	}

	@Column(name = "ORGANIZATION_NAME", length = 100)
	public String getOrganizationName()
	{
		return this.organizationName;
	}

	public void setOrganizationName(String organizationName )
	{
		this.organizationName = organizationName;
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

	@Column(name = "MANAGER_NAME", length = 50)
	public String getManagerName()
	{
		return this.managerName;
	}

	public void setManagerName(String managerName )
	{
		this.managerName = managerName;
	}

	@Column(name = "TEL", length = 55)
	public String getTel()
	{
		return this.tel;
	}

	public void setTel(String tel )
	{
		this.tel = tel;
	}

	@Column(name = "ADDRESS", length = 200)
	public String getAddress()
	{
		return this.address;
	}

	public void setAddress(String address )
	{
		this.address = address;
	}

	@Column(name = "DESCRIPTION", length = 500)
	public String getDescription()
	{
		return this.description;
	}

	public void setDescription(String description )
	{
		this.description = description;
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