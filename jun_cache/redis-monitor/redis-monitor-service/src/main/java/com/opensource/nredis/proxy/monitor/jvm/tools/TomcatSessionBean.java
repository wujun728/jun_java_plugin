package com.opensource.nredis.proxy.monitor.jvm.tools;

public class TomcatSessionBean {
	
	private String frameworkname;
	
	private Long maxActiveSessions;
	
	private Long activeSessions;
	
	private Long sessionCounter;

	/**
	 * @param frameworkname
	 * @param maxActiveSessions
	 * @param activeSessions
	 * @param sessionCounter
	 */
	public TomcatSessionBean(String frameworkname, Long maxActiveSessions,
			Long activeSessions, Long sessionCounter) {
		super();
		this.frameworkname = frameworkname;
		this.maxActiveSessions = maxActiveSessions;
		this.activeSessions = activeSessions;
		this.sessionCounter = sessionCounter;
	}

	/**
	 * 
	 */
	public TomcatSessionBean() {
		super();
	}

	/**
	 * @return the frameworkname
	 */
	public String getFrameworkname() {
		return frameworkname;
	}

	/**
	 * @param frameworkname the frameworkname to set
	 */
	public void setFrameworkname(String frameworkname) {
		this.frameworkname = frameworkname;
	}

	/**
	 * @return the maxActiveSessions
	 */
	public Long getMaxActiveSessions() {
		return maxActiveSessions;
	}

	/**
	 * @param maxActiveSessions the maxActiveSessions to set
	 */
	public void setMaxActiveSessions(Long maxActiveSessions) {
		this.maxActiveSessions = maxActiveSessions;
	}

	/**
	 * @return the activeSessions
	 */
	public Long getActiveSessions() {
		return activeSessions;
	}

	/**
	 * @param activeSessions the activeSessions to set
	 */
	public void setActiveSessions(Long activeSessions) {
		this.activeSessions = activeSessions;
	}

	/**
	 * @return the sessionCounter
	 */
	public Long getSessionCounter() {
		return sessionCounter;
	}

	/**
	 * @param sessionCounter the sessionCounter to set
	 */
	public void setSessionCounter(Long sessionCounter) {
		this.sessionCounter = sessionCounter;
	}
	
	
}
