/**
 * 
 */
package com.opensource.nredis.proxy.monitor.model;

/**
 * @author liubing
 *
 */
public class RedisConfigInfo {
	
	private String timestamp;//时间
	
	private Integer clientConnection;//客户端连接数
	
	private Integer totalCommands;//总命令数
	
	private Double sysMemory;//系统分配内存
	
	private Double usedMemory;//使用内存
	
	private Double keyHits;//命令次数
	
	private Double keyMiss;//失败次数
	
	private Double usedSystemCPU;//系统使用cpu
	
	private Double usedHumanCPU;//redis使用cpu

	/**
	 * @return the timestamp
	 */
	public String getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the clientConnection
	 */
	public Integer getClientConnection() {
		return clientConnection;
	}

	/**
	 * @param clientConnection the clientConnection to set
	 */
	public void setClientConnection(Integer clientConnection) {
		this.clientConnection = clientConnection;
	}

	/**
	 * @return the totalCommands
	 */
	public Integer getTotalCommands() {
		return totalCommands;
	}

	/**
	 * @param totalCommands the totalCommands to set
	 */
	public void setTotalCommands(Integer totalCommands) {
		this.totalCommands = totalCommands;
	}

	/**
	 * @return the sysMemory
	 */
	public Double getSysMemory() {
		return sysMemory;
	}

	/**
	 * @param sysMemory the sysMemory to set
	 */
	public void setSysMemory(Double sysMemory) {
		this.sysMemory = sysMemory;
	}

	/**
	 * @return the usedMemory
	 */
	public Double getUsedMemory() {
		return usedMemory;
	}

	/**
	 * @param usedMemory the usedMemory to set
	 */
	public void setUsedMemory(Double usedMemory) {
		this.usedMemory = usedMemory;
	}

	/**
	 * @return the keyHits
	 */
	public Double getKeyHits() {
		return keyHits;
	}

	/**
	 * @param keyHits the keyHits to set
	 */
	public void setKeyHits(Double keyHits) {
		this.keyHits = keyHits;
	}

	/**
	 * @return the usedSystemCPU
	 */
	public Double getUsedSystemCPU() {
		return usedSystemCPU;
	}

	/**
	 * @param usedSystemCPU the usedSystemCPU to set
	 */
	public void setUsedSystemCPU(Double usedSystemCPU) {
		this.usedSystemCPU = usedSystemCPU;
	}

	/**
	 * @return the usedHumanCPU
	 */
	public Double getUsedHumanCPU() {
		return usedHumanCPU;
	}

	/**
	 * @param usedHumanCPU the usedHumanCPU to set
	 */
	public void setUsedHumanCPU(Double usedHumanCPU) {
		this.usedHumanCPU = usedHumanCPU;
	}

	/**
	 * @return the keyMiss
	 */
	public Double getKeyMiss() {
		return keyMiss;
	}

	/**
	 * @param keyMiss the keyMiss to set
	 */
	public void setKeyMiss(Double keyMiss) {
		this.keyMiss = keyMiss;
	}
	
	
	
}
