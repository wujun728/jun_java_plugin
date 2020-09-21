package com.osmp.web.system.dict.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Description: 数据字典表
 * 
 * @author: wangkaiping
 * @date: 2014年11月13日 下午5:40:42
 */
@Table(name = "t_dict")
public class Dict {

	/**
	 * 字典类型 数据来源于字典表ITEM
	 */
	public static final int DTYPE_DICTTABLE = 1;

	/**
	 * 字典类型 数据来源于其它表里的两个字段
	 */
	public static final int DTYPE_OTHERTABLE = 2;

	/**
	 * 字典类型 数据来源于资源配置文件 properties
	 */
	public static final int DTYPE_PROPERTIES = 3;

	/**
	 * 主键ID UUID生成
	 */
	@Id
	private String id;

	/**
	 * 字典CODE
	 */
	@Column
	private String code;

	/**
	 * 字典名称
	 */
	@Column
	private String name;

	/**
	 * 字典类型 对应上面的
	 */
	@Column
	private int type;

	/**
	 * 当字类型为DTYPE_OTHERTABLE（2）时定义的表名称
	 */
	@Column
	private String tabName;

	/**
	 * 当字类型为DTYPE_OTHERTABLE（2）时定义的KEY 字段名称
	 */
	@Column
	private String keyFilde;

	/**
	 * 当字类型为DTYPE_OTHERTABLE（2）时定义的VALUE 字段名称
	 */
	@Column
	private String valueFilde;

	/**
	 * 当字类型为DTYPE_PROPERTIES（3）时定义的资源文件路径及名称
	 */
	@Column
	private String propertiesFile;

	/**
	 * 数据字典项
	 */
	private List<DictItem> dictItem;

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTabName() {
		return tabName;
	}

	public void setTabName(String tabName) {
		this.tabName = tabName;
	}

	public String getKeyFilde() {
		return keyFilde;
	}

	public void setKeyFilde(String keyFilde) {
		this.keyFilde = keyFilde;
	}

	public String getValueFilde() {
		return valueFilde;
	}

	public void setValueFilde(String valueFilde) {
		this.valueFilde = valueFilde;
	}

	public String getPropertiesFile() {
		return propertiesFile;
	}

	public void setPropertiesFile(String propertiesFile) {
		this.propertiesFile = propertiesFile;
	}

	public List<DictItem> getDictItem() {
		return dictItem;
	}

	public void setDictItem(List<DictItem> dictItem) {
		this.dictItem = dictItem;
	}

}
