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
 * SystemCode entity. @author Wujun
 */
@Entity
@Table(name = "SYS_SYSTEM_CODE")
@DynamicUpdate(true)
@DynamicInsert(true)
public class SystemCode implements java.io.Serializable
{
	private static final long serialVersionUID = -5522136453981132093L;
	private Integer codeId;
	private String codeMyid;
	private String name;
	private Integer sort;
	private String iconCls;
	private String state;
	private String type;
	private Integer parentId;
	private Integer permissionId;
	private String description;
	private String status;
	private Date created;
	private Date lastmod;
	private Integer creater;
	private Integer modifyer;

	// Constructors

	/** default constructor */
	public SystemCode()
	{
	}

	/** full constructor */
	public SystemCode(String codeMyid, String name, Integer sort, Integer parentId,
			String description, String status, Date created, Date lastmod, Integer creater,
			Integer modifyer)
	{
		this.codeMyid = codeMyid;
		this.name = name;
		this.sort = sort;
		this.parentId = parentId;
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
	@Column(name = "CODE_ID", unique = true, nullable = false)
	public Integer getCodeId()
	{
		return this.codeId;
	}

	public void setCodeId(Integer codeId )
	{
		this.codeId = codeId;
	}

	@Column(name = "CODE_MYID", length = 100)
	public String getCodeMyid()
	{
		return this.codeMyid;
	}

	public void setCodeMyid(String codeMyid )
	{
		this.codeMyid = codeMyid;
	}

	@Column(name = "NAME")
	public String getName()
	{
		return this.name;
	}

	public void setName(String name )
	{
		this.name = name;
	}

	@Column(name = "SORT")
	public Integer getSort()
	{
		return this.sort;
	}

	public void setSort(Integer sort )
	{
		this.sort = sort;
	}

	@Column(name = "PARENT_ID")
	public Integer getParentId()
	{
		return this.parentId;
	}

	public void setParentId(Integer parentId )
	{
		this.parentId = parentId;
	}
	
	@Column(name = "PERMISSIONID")
	public Integer getPermissionId()
	{
		return permissionId;
	}

	public void setPermissionId(Integer permissionId )
	{
		this.permissionId = permissionId;
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
	
	@Column(name = "TYPE", length = 1)
	public String getType()
	{
		return type;
	}

	public void setType(String type )
	{
		this.type = type;
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
	
	@Column(name = "STATE",length = 20)
	public String getState()
	{
		return this.state;
	}

	public void setState(String state )
	{
		this.state = state;
	}
	
	@Column(name = "ICONCLS", length = 100)
	public String getIconCls()
	{
		return iconCls;
	}
	public void setIconCls(String iconCls )
	{
		this.iconCls = iconCls;
	}
}