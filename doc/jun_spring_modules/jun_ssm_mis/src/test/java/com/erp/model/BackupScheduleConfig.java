package com.erp.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(name = "BackupScheduleConfig")
public class BackupScheduleConfig implements java.io.Serializable
{
	private static final long serialVersionUID = 1152795886234695671L;
	private Integer scheduleHour;
	private Integer scheduleSecond;
	private Integer scheduleMinute;
	private String scheduleEnabled;

	public BackupScheduleConfig()
	{
	}

	public BackupScheduleConfig(Integer scheduleHour, Integer scheduleSecond,
			Integer scheduleMinute, String scheduleEnabled)
	{
		this.scheduleHour = scheduleHour;
		this.scheduleSecond = scheduleSecond;
		this.scheduleMinute = scheduleMinute;
		this.scheduleEnabled = scheduleEnabled;
	}

	public Integer getScheduleHour()
	{
		return this.scheduleHour;
	}

	public void setScheduleHour(Integer scheduleHour )
	{
		this.scheduleHour = scheduleHour;
	}

	public Integer getScheduleSecond()
	{
		return this.scheduleSecond;
	}

	public void setScheduleSecond(Integer scheduleSecond )
	{
		this.scheduleSecond = scheduleSecond;
	}

	public Integer getScheduleMinute()
	{
		return this.scheduleMinute;
	}

	public void setScheduleMinute(Integer scheduleMinute )
	{
		this.scheduleMinute = scheduleMinute;
	}

	public String getScheduleEnabled()
	{
		return this.scheduleEnabled;
	}

	public void setScheduleEnabled(String scheduleEnabled )
	{
		this.scheduleEnabled = scheduleEnabled;
	}

}