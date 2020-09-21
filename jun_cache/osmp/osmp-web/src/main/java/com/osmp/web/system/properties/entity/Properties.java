package com.osmp.web.system.properties.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Description: 系统资源文件表
 * 
 * @author: wangkaiping
 * @date: 2014年11月13日 下午5:40:42
 */
@Table(name = "t_properties")
public class Properties {

	/**
	 * 主键ID UUID生成
	 */
	@Id
	private String id;

	/**
	 * 字典名称
	 */
	@Column
	private String name;

	/**
	 * KEY
	 */
	@Column
	private String prokey;

	/**
	 * VALUE
	 */
	@Column
	private String provalue;

	/**
	 * 备注
	 */
	@Column
	private String remark;

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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getProkey() {
		return prokey;
	}

	public void setProkey(String prokey) {
		this.prokey = prokey;
	}

	public String getProvalue() {
		return provalue;
	}

	public void setProvalue(String provalue) {
		this.provalue = provalue;
	}

}
