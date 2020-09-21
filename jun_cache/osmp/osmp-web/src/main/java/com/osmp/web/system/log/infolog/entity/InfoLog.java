package com.osmp.web.system.log.infolog.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 菜单及权限信息
 * 
 * @author zhangjunming
 * @version V1.0, 2014-11-13 下午17:24:34
 */
@Table(name = "infoLog")
public class InfoLog {
	/**
	 * ID
	 */
	@Id
	private int id = 0;

	/**
	 * 错误来源
	 */
	@Column
	private String bundle;

	@Column
	private String version;
	/**
	 * 错误信息
	 */
	@Column
	private String service;

	/**
	 * 日志内容
	 */
	@Column
	private String message;

	/**
	 * 日志发生时间
	 */
	@Column
	private long occurTime;

	@Column
	private Date insertTime;

	@Column
	private String loadIp;

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

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getOccurTime() {
		return occurTime;
	}

	public void setOccurTime(long occurTime) {
		this.occurTime = occurTime;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public String getLoadIp() {
		return loadIp;
	}

	public void setLoadIp(String loadIp) {
		this.loadIp = loadIp;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
}
