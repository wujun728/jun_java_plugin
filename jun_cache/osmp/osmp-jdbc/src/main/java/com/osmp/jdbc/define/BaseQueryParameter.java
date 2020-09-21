/*   
 * Project: OSMP
 * FileName: TransactionWrapper.java
 * version: V1.0
 */
package com.osmp.jdbc.define;

import java.io.Serializable;

/**
 * Description:查询条件
 * 
 * @author: wangkaiping
 * @date: 2014年9月10日 上午11:27:08
 */

public class BaseQueryParameter implements Serializable {

	private static final long serialVersionUID = 1L;

	protected Object id;
	protected String code;
	protected String name;

	public Object getId() {
		return id;
	}

	public void setId(Object id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}