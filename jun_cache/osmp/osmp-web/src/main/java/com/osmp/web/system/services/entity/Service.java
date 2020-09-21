package com.osmp.web.system.services.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "dataServices")
public class Service {
	
	@Id
	private int id;
	@Column
	private String bundle;
	@Column
	private String version;
	@Column
	private String name;
	@Column
	private int state;
	@Column
	private Date updateTime;
	@Column
	private String mark;
	@Column
	private String loadIP;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBundle() {
		return bundle;
	}
	public void setBundle(String bundle) {
		this.bundle = bundle;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public String getLoadIP() {
		return loadIP;
	}
	public void setLoadIP(String loadIP) {
		this.loadIP = loadIP;
	}
	
	
}
