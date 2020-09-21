package com.osmp.web.system.sermapping.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "dataServiceMapping")
public class SerMapping {

	@Id
	private int id;
	
	@Column
	private String bundle;
	
	@Column
	private String version;
	
	@Column
	private String serviceName;
	
	@Column
	private String interfaceName;
	
	@Column
	private Date updateTime;

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

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "SerMapping [id=" + id + ", bundle=" + bundle + ", version="
				+ version + ", serviceName=" + serviceName + ", interfaceName="
				+ interfaceName + ", updateTime=" + updateTime + "]";
	}
}
