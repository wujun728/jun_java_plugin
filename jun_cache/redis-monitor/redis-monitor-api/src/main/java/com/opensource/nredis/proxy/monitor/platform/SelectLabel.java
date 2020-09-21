/**
 * 
 */
package com.opensource.nredis.proxy.monitor.platform;

/**
 * @author liubing
 *
 */
public class SelectLabel {
	
	private String label;
	private String value;
	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @param label
	 * @param value
	 */
	public SelectLabel(String label, String value) {
		super();
		this.label = label;
		this.value = value;
	}
	
	
}
