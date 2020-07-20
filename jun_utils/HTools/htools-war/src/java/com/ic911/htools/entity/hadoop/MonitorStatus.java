package com.ic911.htools.entity.hadoop;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.ic911.core.domain.BaseEntity;
import com.ic911.core.hadoop.domain.NodeStatus;
/**
 * 监控信息记录
 * @author murenchao
 */
@Entity
@Table(name = "hdp_monitor_status")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MonitorStatus extends BaseEntity {
	private static final long serialVersionUID = -4960278731538685112L;

	@NotNull
	@Column(nullable = false)
	private String type;
	@NotNull
	@Column(nullable = false)
	private String ip;
	@NotNull
	@Column(nullable = false)
	private String hostname;
	@NotNull
	@Column(nullable = false)
	private NodeStatus status;
	@NotNull
	@Column(nullable = false)
	private Date moitorDate;
	@NotNull
	@Column(nullable = false)
	private int yeas;
	@NotNull
	@Column(nullable = false)
	private int month; 
	@NotNull
	@Column(nullable = false)
	private int day;
	@NotNull
	@Column(nullable = false)
	private int hours;
	@NotNull
	@Column(nullable = false)
	private int minute;
	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public NodeStatus getStatus() {
		return status;
	}

	public void setStatus(NodeStatus status) {
		this.status = status;
	}
	
	public Date getMoitorDate() {
		return moitorDate;
	}

	public void setMoitorDate(Date moitorDate) {
		this.moitorDate = moitorDate;
	}

	public int getYeas() {
		return yeas;
	}

	public void setYeas(int yeas) {
		this.yeas = yeas;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

}
