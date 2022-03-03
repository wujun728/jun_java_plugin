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
 * Currency entity. @author Wujun
 */
@Entity
@Table(name = "SYS_CURRENCY")
@DynamicUpdate(true)
@DynamicInsert(true)
public class Currency implements java.io.Serializable
{
	private static final long serialVersionUID = -1925563959210881165L;
	private Integer id;
	private String name;
	private Date created;
	private Date lastmod;
	private Integer creater;
	private Integer modifyer;
	private String sign;
	private String code;

	// Constructors

	/** default constructor */
	public Currency()
	{
	}

	/** minimal constructor */
	public Currency(String name)
	{
		this.name = name;
	}

	/** full constructor */
	public Currency(String name, Date created, Date lastmod, Integer creater, Integer modifyer,
			String sign, String code)
	{
		this.name = name;
		this.created = created;
		this.lastmod = lastmod;
		this.creater = creater;
		this.modifyer = modifyer;
		this.sign = sign;
		this.code = code;
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

	@Column(name = "NAME", nullable = false, length = 55)
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

	@Column(name = "SIGN", length = 55)
	public String getSign()
	{
		return this.sign;
	}

	public void setSign(String sign )
	{
		this.sign = sign;
	}

	@Column(name = "CODE", length = 55)
	public String getCode()
	{
		return this.code;
	}

	public void setCode(String code )
	{
		this.code = code;
	}

}