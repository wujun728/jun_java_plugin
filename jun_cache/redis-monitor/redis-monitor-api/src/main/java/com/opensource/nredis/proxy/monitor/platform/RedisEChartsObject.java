/**
 * 
 */
package com.opensource.nredis.proxy.monitor.platform;

import com.github.abel533.echarts.Option;

/**
 * @author liubing
 *
 */
public class RedisEChartsObject {
	
	private Option clientConnectionOption;
	
	private Option totalCommandOption;
	
	private Option keyHitOption;
	
	private Option usedCPUOption;//

	private Option usedMemoryption;

	/**
	 * @return the clientConnectionOption
	 */
	public Option getClientConnectionOption() {
		return clientConnectionOption;
	}

	/**
	 * @param clientConnectionOption the clientConnectionOption to set
	 */
	public void setClientConnectionOption(Option clientConnectionOption) {
		this.clientConnectionOption = clientConnectionOption;
	}

	/**
	 * @return the totalCommandOption
	 */
	public Option getTotalCommandOption() {
		return totalCommandOption;
	}

	/**
	 * @param totalCommandOption the totalCommandOption to set
	 */
	public void setTotalCommandOption(Option totalCommandOption) {
		this.totalCommandOption = totalCommandOption;
	}

	/**
	 * @return the keyHitOption
	 */
	public Option getKeyHitOption() {
		return keyHitOption;
	}

	/**
	 * @param keyHitOption the keyHitOption to set
	 */
	public void setKeyHitOption(Option keyHitOption) {
		this.keyHitOption = keyHitOption;
	}

	/**
	 * @return the usedCPUOption
	 */
	public Option getUsedCPUOption() {
		return usedCPUOption;
	}

	/**
	 * @param usedCPUOption the usedCPUOption to set
	 */
	public void setUsedCPUOption(Option usedCPUOption) {
		this.usedCPUOption = usedCPUOption;
	}

	/**
	 * @return the usedMemoryption
	 */
	public Option getUsedMemoryption() {
		return usedMemoryption;
	}

	/**
	 * @param usedMemoryption the usedMemoryption to set
	 */
	public void setUsedMemoryption(Option usedMemoryption) {
		this.usedMemoryption = usedMemoryption;
	}
	
	
	
}
