package com.erp.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * Permission entity. @author Wujun
 */
@Entity
@Table(name = "SYS_PERMISSION")
@DynamicUpdate(true)
@DynamicInsert(true)
public class Permission implements java.io.Serializable
{
	private static final long serialVersionUID = 1136795483472903508L;
	private Integer permissionId;
	private Integer pid;
	private String name;
	private String pname;
	private Integer sort;
	private String myid;
	private String type;
	private String state;
	private String isused;
	private String url;
	private String iconCls;
	private String status;
	private String description;
	private Date created;
	private Date lastmod;
	private Integer creater;
	private Integer modifyer;
	private Set<RolePermission> rolePermissions = new HashSet<RolePermission>(0);

	// Constructors

	/** default constructor */
	public Permission()
	{
	}

	/** minimal constructor */
	public Permission(String status, Date created)
	{
		this.status = status;
		this.created = created;
	}

	/** full constructor */
	public Permission(Integer pid, String name, String pname, Integer sort, String myid,
			String type, String isused, String url, String iconCls, String status,
			String description, Date created, Date lastmod, Integer creater, Integer modifyer,
			Set<RolePermission> rolePermissions)
	{
		this.pid = pid;
		this.name = name;
		this.pname = pname;
		this.sort = sort;
		this.myid = myid;
		this.type = type;
		this.isused = isused;
		this.url = url;
		this.iconCls = iconCls;
		this.status = status;
		this.description = description;
		this.created = created;
		this.lastmod = lastmod;
		this.creater = creater;
		this.modifyer = modifyer;
		this.rolePermissions = rolePermissions;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "PERMISSION_ID", unique = true, nullable = false)
	public Integer getPermissionId()
	{
		return this.permissionId;
	}

	public void setPermissionId(Integer permissionId )
	{
		this.permissionId = permissionId;
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

	@Column(name = "NAME", length = 100)
	public String getName()
	{
		return this.name;
	}

	public void setName(String name )
	{
		this.name = name;
	}

	@Column(name = "PNAME", length = 100)
	public String getPname()
	{
		return this.pname;
	}

	public void setPname(String pname )
	{
		this.pname = pname;
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

	@Column(name = "MYID", length = 55)
	public String getMyid()
	{
		return this.myid;
	}

	public void setMyid(String myid )
	{
		this.myid = myid;
	}

	@Column(name = "TYPE", length = 1)
	public String getType()
	{
		return this.type;
	}

	public void setType(String type )
	{
		this.type = type;
	}

	@Column(name = "ISUSED", length = 1)
	public String getIsused()
	{
		return this.isused;
	}

	public void setIsused(String isused )
	{
		this.isused = isused;
	}

	@Column(name = "URL", length = 200)
	public String getUrl()
	{
		return this.url;
	}

	public void setUrl(String url )
	{
		this.url = url;
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
	@Column(name = "STATUS", nullable = false, length = 1)
	public String getStatus()
	{
		return this.status;
	}


	public void setStatus(String status )
	{
		this.status = status;
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED", nullable = false, length = 10)
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "permission")
	public Set<RolePermission> getRolePermissions()
	{
		return this.rolePermissions;
	}

	public void setRolePermissions(Set<RolePermission> rolePermissions )
	{
		this.rolePermissions = rolePermissions;
	}

}