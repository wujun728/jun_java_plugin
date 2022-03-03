package com.erp.viewModel;

public class TreeModel
{
	private String id;
	private String pid;
	private String name;
	private String iconCls;
	private String state;
	private Integer permissionId;
	private Attributes attributes;
	
	public Attributes getAttributes()
	{
		return attributes;
	}
	public void setAttributes(Attributes attributes )
	{
		this.attributes = attributes;
	}
	public Integer getPermissionId()
	{
		return permissionId;
	}
	public void setPermissionId(Integer permissionId )
	{
		this.permissionId = permissionId;
	}
	public String getIconCls()
	{
		return iconCls;
	}
	public void setIconCls(String iconCls )
	{
		this.iconCls = iconCls;
	}
	public String getState()
	{
		return state;
	}
	public void setState(String state )
	{
		this.state = state;
	}
	public String getId()
	{
		return id;
	}
	public void setId(String id )
	{
		this.id = id;
	}
	public String getPid()
	{
		return pid;
	}
	public void setPid(String pid )
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
	
}
