package com.opensource.nredis.proxy.monitor.jvm.tools;

public class CPUBean {
	
	private Long processCpuTime;
	private Integer availableProcessors;
	/**
	 * @return the processCpuTime
	 */
	public Long getProcessCpuTime() {
		return processCpuTime;
	}
	/**
	 * @param processCpuTime the processCpuTime to set
	 */
	public void setProcessCpuTime(Long processCpuTime) {
		this.processCpuTime = processCpuTime;
	}
	/**
	 * @return the availableProcessors
	 */
	public Integer getAvailableProcessors() {
		return availableProcessors;
	}
	/**
	 * @param availableProcessors the availableProcessors to set
	 */
	public void setAvailableProcessors(Integer availableProcessors) {
		this.availableProcessors = availableProcessors;
	}
	/**
	 * @param processCpuTime
	 * @param availableProcessors
	 */
	public CPUBean(Long processCpuTime, Integer availableProcessors) {
		super();
		this.processCpuTime = processCpuTime;
		this.availableProcessors = availableProcessors;
	}
	/**
	 * 
	 */
	public CPUBean() {
		super();
	}
	
	
}
