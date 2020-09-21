/**
 * 
 */
package com.opensource.nredis.proxy.monitor.platform;

import com.github.abel533.echarts.Option;

/**
 * @author liubing
 *
 */
public class EChartsObject {
	
	private DataGridObject quartzRuntimeDataGridObject;
	
	private DataGridObject heapMemroryGarbageObject;
	
	private Option jvmInfoOption;
	
	private Option jvmUsedOption;
	
	private Option cpuUsedOption;
	
	private Option jvmClassLoaderOption;//tomcat线程

	private Option jvmThreadOption;
	
	private Option tomcatThreadOption;

	/**
	 * @return the quartzRuntimeDataGridObject
	 */
	public DataGridObject getQuartzRuntimeDataGridObject() {
		return quartzRuntimeDataGridObject;
	}

	/**
	 * @param quartzRuntimeDataGridObject the quartzRuntimeDataGridObject to set
	 */
	public void setQuartzRuntimeDataGridObject(
			DataGridObject quartzRuntimeDataGridObject) {
		this.quartzRuntimeDataGridObject = quartzRuntimeDataGridObject;
	}

	/**
	 * @return the heapMemroryGarbageObject
	 */
	public DataGridObject getHeapMemroryGarbageObject() {
		return heapMemroryGarbageObject;
	}

	/**
	 * @param heapMemroryGarbageObject the heapMemroryGarbageObject to set
	 */
	public void setHeapMemroryGarbageObject(DataGridObject heapMemroryGarbageObject) {
		this.heapMemroryGarbageObject = heapMemroryGarbageObject;
	}

	/**
	 * @return the jvmInfoOption
	 */
	public Option getJvmInfoOption() {
		return jvmInfoOption;
	}

	/**
	 * @param jvmInfoOption the jvmInfoOption to set
	 */
	public void setJvmInfoOption(Option jvmInfoOption) {
		this.jvmInfoOption = jvmInfoOption;
	}

	/**
	 * @return the jvmUsedOption
	 */
	public Option getJvmUsedOption() {
		return jvmUsedOption;
	}

	/**
	 * @param jvmUsedOption the jvmUsedOption to set
	 */
	public void setJvmUsedOption(Option jvmUsedOption) {
		this.jvmUsedOption = jvmUsedOption;
	}

	/**
	 * @return the cpuUsedOption
	 */
	public Option getCpuUsedOption() {
		return cpuUsedOption;
	}

	/**
	 * @param cpuUsedOption the cpuUsedOption to set
	 */
	public void setCpuUsedOption(Option cpuUsedOption) {
		this.cpuUsedOption = cpuUsedOption;
	}

	/**
	 * @return the jvmClassLoaderOption
	 */
	public Option getJvmClassLoaderOption() {
		return jvmClassLoaderOption;
	}

	/**
	 * @param jvmClassLoaderOption the jvmClassLoaderOption to set
	 */
	public void setJvmClassLoaderOption(Option jvmClassLoaderOption) {
		this.jvmClassLoaderOption = jvmClassLoaderOption;
	}

	/**
	 * @return the jvmThreadOption
	 */
	public Option getJvmThreadOption() {
		return jvmThreadOption;
	}

	/**
	 * @param jvmThreadOption the jvmThreadOption to set
	 */
	public void setJvmThreadOption(Option jvmThreadOption) {
		this.jvmThreadOption = jvmThreadOption;
	}

	/**
	 * @return the tomcatThreadOption
	 */
	public Option getTomcatThreadOption() {
		return tomcatThreadOption;
	}

	/**
	 * @param tomcatThreadOption the tomcatThreadOption to set
	 */
	public void setTomcatThreadOption(Option tomcatThreadOption) {
		this.tomcatThreadOption = tomcatThreadOption;
	}
	
	
	
}
