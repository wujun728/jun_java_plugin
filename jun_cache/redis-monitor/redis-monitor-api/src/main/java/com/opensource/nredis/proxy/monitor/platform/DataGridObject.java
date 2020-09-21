/**
 * 
 */
package com.opensource.nredis.proxy.monitor.platform;

import java.io.Serializable;
import java.util.List;

/**
 * @author liubing
 *
 */
public class DataGridObject implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2004684972256455022L;

	private List rows;
	
	private int total;
	
	/**
	 * 
	 */
	public DataGridObject() {
		super();
	}

	/**
	 * @param rows
	 * @param total
	 */
	public DataGridObject(List rows, int total) {
		super();
		this.rows = rows;
		this.total = total;
	}

	/**
	 * @return the rows
	 */
	public List getRows() {
		return rows;
	}

	/**
	 * @param rows the rows to set
	 */
	public void setRows(List rows) {
		this.rows = rows;
	}

	/**
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(int total) {
		this.total = total;
	}
	
	
}
