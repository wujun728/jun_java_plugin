package com.erp.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * RolePermission entity. @author Wujun
 */
@Entity
@Table(name = "SYS_ROLE_PERMISSION")
@DynamicUpdate(true)
@DynamicInsert(true)
public class RolePermission implements java.io.Serializable
{
	private static final long serialVersionUID = 1167900432405270755L;
	private Integer id;
	private Role role;
	private Permission permission;
	private String status;
	private Date created;
	private Date lastmod;
	private Integer creater;
	private Integer modifyer;

	// Constructors

	/** default constructor */
	public RolePermission()
	{
	}

	/** minimal constructor */
	public RolePermission(Role role, Permission permission)
	{
		this.role = role;
		this.permission = permission;
	}

	/** full constructor */
	public RolePermission(Role role, Permission permission, String status, Date created,
			Date lastmod, Integer creater, Integer modifyer)
	{
		this.role = role;
		this.permission = permission;
		this.status = status;
		this.created = created;
		this.lastmod = lastmod;
		this.creater = creater;
		this.modifyer = modifyer;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId()
	{
		return this.id;
	}

	public void setId(Integer id )
	{
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROLE_ID", nullable = false)
	public Role getRole()
	{
		return this.role;
	}

	public void setRole(Role role )
	{
		this.role = role;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PERMISSION_ID", nullable = false)
	public Permission getPermission()
	{
		return this.permission;
	}

	public void setPermission(Permission permission )
	{
		this.permission = permission;
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