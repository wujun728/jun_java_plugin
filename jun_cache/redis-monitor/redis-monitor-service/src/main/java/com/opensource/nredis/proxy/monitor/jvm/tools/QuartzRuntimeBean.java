/**
 * 
 */
package com.opensource.nredis.proxy.monitor.jvm.tools;

/**
 * quartz定时器
 * @author liubing
 *
 */
public class QuartzRuntimeBean {
	
	private String schedulerInstanceId;
	
	private String schedulerName;
	
	private Integer status;
	
	private String ThreadPoolClassName;
	
	private Integer ThreadPoolSize;
	
	private String version;//版本号
	
	private String statusName;//
	/**
	 * @return the schedulerInstanceId
	 */
	public String getSchedulerInstanceId() {
		return schedulerInstanceId;
	}

	/**
	 * @param schedulerInstanceId the schedulerInstanceId to set
	 */
	public void setSchedulerInstanceId(String schedulerInstanceId) {
		this.schedulerInstanceId = schedulerInstanceId;
	}

	/**
	 * @return the schedulerName
	 */
	public String getSchedulerName() {
		return schedulerName;
	}

	/**
	 * @param schedulerName the schedulerName to set
	 */
	public void setSchedulerName(String schedulerName) {
		this.schedulerName = schedulerName;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return the threadPoolClassName
	 */
	public String getThreadPoolClassName() {
		return ThreadPoolClassName;
	}

	/**
	 * @param threadPoolClassName the threadPoolClassName to set
	 */
	public void setThreadPoolClassName(String threadPoolClassName) {
		ThreadPoolClassName = threadPoolClassName;
	}

	/**
	 * @return the threadPoolSize
	 */
	public Integer getThreadPoolSize() {
		return ThreadPoolSize;
	}

	/**
	 * @param threadPoolSize the threadPoolSize to set
	 */
	public void setThreadPoolSize(Integer threadPoolSize) {
		ThreadPoolSize = threadPoolSize;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the statusName
	 */
	public String getStatusName() {
		return statusName;
	}

	/**
	 * @param statusName the statusName to set
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	
	
}
