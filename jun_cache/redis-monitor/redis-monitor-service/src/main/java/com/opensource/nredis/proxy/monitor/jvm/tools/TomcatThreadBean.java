/**
 * 
 */
package com.opensource.nredis.proxy.monitor.jvm.tools;

/**
 * @author liubing
 *
 */
public class TomcatThreadBean {
	
	private Long maxThreads;//最大线程
	
	private Long currentThreadCount;//当前线程总数
	
	private Long currentThreadsBusy;//繁忙线程
	
	private int status;//当前状态
	
	private String name;//名称
	

	/**
	 * @return the maxThreads
	 */
	public Long getMaxThreads() {
		return maxThreads;
	}

	/**
	 * @param maxThreads the maxThreads to set
	 */
	public void setMaxThreads(Long maxThreads) {
		this.maxThreads = maxThreads;
	}

	/**
	 * @return the currentThreadCount
	 */
	public Long getCurrentThreadCount() {
		return currentThreadCount;
	}

	/**
	 * @param currentThreadCount the currentThreadCount to set
	 */
	public void setCurrentThreadCount(Long currentThreadCount) {
		this.currentThreadCount = currentThreadCount;
	}

	/**
	 * @return the currentThreadsBusy
	 */
	public Long getCurrentThreadsBusy() {
		return currentThreadsBusy;
	}

	/**
	 * @param currentThreadsBusy the currentThreadsBusy to set
	 */
	public void setCurrentThreadsBusy(Long currentThreadsBusy) {
		this.currentThreadsBusy = currentThreadsBusy;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	
}
