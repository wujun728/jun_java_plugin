/**<p>项目名：</p>
 * <p>包名：	com.zttx.redis.bean</p>
 * <p>文件名：U.java</p>
 * <p>版本信息：</p>
 * <p>日期：2015年1月14日-下午1:47:36</p>
 * Copyright (c) 2015singno公司-版权所有
 */
package com.zttx.redis.bean;

import java.io.Serializable;

/**<p>名称：U.java</p>
 * <p>描述：</p>
 * <pre>
 *         
 * </pre>
 * @author 鲍建明
 * @date 2015年1月14日 下午1:47:36
 * @version 1.0.0
 */
public class U implements Serializable {

	
	
	private static final long serialVersionUID = 7102873147877263336L;
	private String id;
	private String name;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "U [id=" + id + ", name=" + name + "]";
	}
}
