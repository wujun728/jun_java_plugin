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
 * Parameter entity. @author Wujun
 */
@Entity
@Table(name = "SYS_PARAMETER")
@DynamicUpdate(true)
@DynamicInsert(true)
public class Parameter implements java.io.Serializable
{
	private static final long serialVersionUID = -7676187590856494738L;
	private Integer parameterId;
	private String myid;
	private String name;
	private String value;
	private String description;
	private String group;
	private String state;
	private String status;
	private String editorType;
	private String editor;
	private Date created;
	private Date lastmod;
	private Integer creater;
	private Integer modifyer;

	// Constructors

	/** default constructor */
	public Parameter()
	{
	}

	/** full constructor */
	public Parameter(String myid, String name, String value, String description, String status,
			Date created, Date lastmod, Integer creater, Integer modifyer)
	{
		this.myid = myid;
		this.name = name;
		this.value = value;
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
	@Column(name = "PARAMETER_ID", unique = true, nullable = false)
	public Integer getParameterId()
	{
		return this.parameterId;
	}

	public void setParameterId(Integer parameterId )
	{
		this.parameterId = parameterId;
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

	@Column(name = "NAME", length = 100)
	public String getName()
	{
		return this.name;
	}

	public void setName(String name )
	{
		this.name = name;
	}
	@Column(name = "EDITOR_TYPE", length = 100)
	public String getEditorType()
	{
		return editorType;
	}

	public void setEditorType(String editorType )
	{
		this.editorType = editorType;
	}
	
	@Column(name = "EDITOR", length = 100)
	public String getEditor()
	{
		return editor;
	}

	public void setEditor(String editor )
	{
		this.editor = editor;
	}

	@Column(name = "VALUE")
	public String getValue()
	{
		return this.value;
	}

	

	public void setValue(String value )
	{
		this.value = value;
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
	
	@Column(name = "GROUP_TYPE", length = 55)
	public String getGroup()
	{
		return group;
	}

	public void setGroup(String group )
	{
		this.group = group;
	}

	@Column(name = "STATE", length = 1)
	public String getState()
	{
		return state;
	}

	public void setState(String state )
	{
		this.state = state;
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