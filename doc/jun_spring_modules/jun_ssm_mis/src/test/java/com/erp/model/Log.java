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
 * Log entity. @author Wujun
 */
@Entity
@Table(name = "SYS_LOG")
@DynamicUpdate(true)
@DynamicInsert(true)
public class Log implements java.io.Serializable
{
	private static final long serialVersionUID = 1309582605928485828L;
	private Integer logId;
	private Integer userId;
	private String name;
	private Date logDate;
	private Integer type;
	private String mac;
	private String ip;
	private Integer objectType;
	private String objectId;
	private String eventName;
	private String eventRecord;

	// Constructors

	/** default constructor */
	public Log()
	{
	}

	/** full constructor */
	public Log(Integer userId, String name, Date logDate, Integer type, String mac, String ip,
			Integer objectType, String objectId, String eventName, String eventRecord)
	{
		this.userId = userId;
		this.name = name;
		this.logDate = logDate;
		this.type = type;
		this.mac = mac;
		this.ip = ip;
		this.objectType = objectType;
		this.objectId = objectId;
		this.eventName = eventName;
		this.eventRecord = eventRecord;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "LOG_ID", unique = true, nullable = false)
	public Integer getLogId()
	{
		return this.logId;
	}

	public void setLogId(Integer logId )
	{
		this.logId = logId;
	}

	@Column(name = "USER_ID")
	public Integer getUserId()
	{
		return this.userId;
	}

	public void setUserId(Integer userId )
	{
		this.userId = userId;
	}

	@Column(name = "NAME", length = 20)
	public String getName()
	{
		return this.name;
	}

	public void setName(String name )
	{
		this.name = name;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LOG_DATE", length = 10)
	public Date getLogDate()
	{
		return this.logDate;
	}

	public void setLogDate(Date logDate )
	{
		this.logDate = logDate;
	}

	@Column(name = "TYPE")
	public Integer getType()
	{
		return this.type;
	}

	public void setType(Integer type )
	{
		this.type = type;
	}

	@Column(name = "MAC", length = 20)
	public String getMac()
	{
		return this.mac;
	}

	public void setMac(String mac )
	{
		this.mac = mac;
	}

	@Column(name = "IP", length = 20)
	public String getIp()
	{
		return this.ip;
	}

	public void setIp(String ip )
	{
		this.ip = ip;
	}

	@Column(name = "OBJECT_TYPE")
	public Integer getObjectType()
	{
		return this.objectType;
	}

	public void setObjectType(Integer objectType )
	{
		this.objectType = objectType;
	}

	@Column(name = "OBJECT_ID", length = 100)
	public String getObjectId()
	{
		return this.objectId;
	}

	public void setObjectId(String objectId )
	{
		this.objectId = objectId;
	}

	@Column(name = "EVENT_NAME", length = 100)
	public String getEventName()
	{
		return this.eventName;
	}

	public void setEventName(String eventName )
	{
		this.eventName = eventName;
	}

	@Column(name = "EVENT_RECORD", length = 500)
	public String getEventRecord()
	{
		return this.eventRecord;
	}

	public void setEventRecord(String eventRecord )
	{
		this.eventRecord = eventRecord;
	}

}