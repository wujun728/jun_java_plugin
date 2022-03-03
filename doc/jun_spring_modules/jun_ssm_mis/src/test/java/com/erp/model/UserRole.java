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
 * UserRole entity. @author Wujun
 */
@Entity
@Table(name = "SYS_USER_ROLE")
@DynamicUpdate(true)
@DynamicInsert(true)
public class UserRole implements java.io.Serializable
{
	private static final long serialVersionUID = 3995767024135212110L;
	private Integer id;
	private Users users;
	private Role role;
	private String status;
	private Date created;
	private Date lastmod;
	private Integer creater;
	private Integer modifyer;

	// Constructors

	/** default constructor */
	public UserRole()
	{
	}

	/** minimal constructor */
	public UserRole(Users users)
	{
		this.users = users;
	}

	/** full constructor */
	public UserRole(Users users, Role role, String status, Date created, Date lastmod,
			Integer creater, Integer modifyer)
	{
		this.users = users;
		this.role = role;
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
	@JoinColumn(name = "USER_ID", nullable = false)
	public Users getUsers()
	{
		return this.users;
	}

	public void setUsers(Users users )
	{
		this.users = users;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROLE_ID")
	public Role getRole()
	{
		return this.role;
	}

	public void setRole(Role role )
	{
		this.role = role;
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