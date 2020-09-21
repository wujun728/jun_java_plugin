package com.osmp.web.system.dict.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Description:数据字典ITEM
 * 
 * @author: wangkaiping
 * @date: 2014年11月13日 下午6:06:55
 */
@Table(name = "t_dict_item")
public class DictItem {

	@Id
	private String id;

	/**
	 * 数据字典对应的CODE
	 */
	@Column
	private String parentCode;

	/**
	 * 数据字典项名称
	 */
	@Column
	private String name;

	/**
	 * 数据字典值
	 */
	@Column
	private String code;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
