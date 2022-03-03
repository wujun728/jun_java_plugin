package com.erp.viewModel;

import java.util.Date;



public class TreeGridModel
{
	private Integer permissionId;
	private Integer pid;
	private String name;
	private String pname;
	private Integer sort;
	private String myid;
	private String type;
	private String isused;
	private String url;
	private String iconCls;
	private String status;
	private String description;
	private String state;
	private Date created;
	private Date lastmod;
	private Integer creater;
	private Integer modifyer;
	public Integer getCreater()
	{
		return creater;
	}
	public void setCreater(Integer creater )
	{
		this.creater = creater;
	}
	public Integer getModifyer()
	{
		return modifyer;
	}
	public void setModifyer(Integer modifyer )
	{
		this.modifyer = modifyer;
	}
	public Date getCreated()
	{
		return created;
	}
	public void setCreated(Date created )
	{
		this.created = created;
	}
	public Date getLastmod()
	{
		return lastmod;
	}
	public void setLastmod(Date lastmod )
	{
		this.lastmod = lastmod;
	}
	public Integer getPermissionId()
	{
		return permissionId;
	}
	public void setPermissionId(Integer permissionId )
	{
		this.permissionId = permissionId;
	}
	public Integer getPid()
	{
		return pid;
	}
	public void setPid(Integer pid )
	{
		this.pid = pid;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name )
	{
		this.name = name;
	}
	public String getPname()
	{
		return pname;
	}
	public void setPname(String pname )
	{
		this.pname = pname;
	}
	public Integer getSort()
	{
		return sort;
	}
	public void setSort(Integer sort )
	{
		this.sort = sort;
	}
	public String getMyid()
	{
		return myid;
	}
	public void setMyid(String myid )
	{
		this.myid = myid;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type )
	{
		this.type = type;
	}
	public String getIsused()
	{
		return isused;
	}
	public void setIsused(String isused )
	{
		this.isused = isused;
	}
	public String getUrl()
	{
		return url;
	}
	public void setUrl(String url )
	{
		this.url = url;
	}
	public String getIconCls()
	{
		return iconCls;
	}
	public void setIconCls(String iconCls )
	{
		this.iconCls = iconCls;
	}
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status )
	{
		this.status = status;
	}
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description )
	{
		this.description = description;
	}
	public String getState()
	{
		return state;
	}
	public void setState(String state )
	{
		this.state = state;
	}
}
