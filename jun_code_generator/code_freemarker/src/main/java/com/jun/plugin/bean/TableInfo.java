package com.jun.plugin.bean;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 表对象
 * @author Wujun
 */
public class TableInfo {
	/**
	 * 表名
	 */
	private String tableName;

	/**
	 * 前缀
	 */
	private String prefix;

	/**
	 * bean
	 */
	private String beanName;

	/**
	 * 表名
	 */
	private String tableDesc;

	/**
	 * 主键映射
	 */
	private Map<String, String> primaryKey;
	/**
	 * 字段类型映射
	 */
	private List<ColumnInfo> columns;

	/**
	 * 属性,属性类型
	 */
	@Deprecated
	private Map<String, String> properties;

	/**
	 * 属性,属性类型
	 */
	private Map<String, PropertyInfo> propInfoMap;

	/**
	 * 属性,属性类型,属性描述
	 */
	private List<PropertyInfo> allPropInfo;
	/**
	 * 属性,属性类型
	 */
	private Map<String, String> propertiesAnColumns;

	/**
	 * 属性,属性类型
	 */
	private Map<String, String> insertPropertiesAnColumns;

	/**
	 * bean类导入的包,如java.util.Date，java.math.BigDecimal;等
	 */
	private Set<String> propTypePackages;

	private String entityPackage;

	private String daoPackage;

	private String servicePackage;

	private String serviceImplPackage;

	private String controllerPackage;

	private String serviceTestPackage;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public String getTableDesc() {
		return tableDesc;
	}

	public void setTableDesc(String tableDesc) {
		this.tableDesc = tableDesc;
	}

	public Map<String, String> getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(Map<String, String> primaryKey) {
		this.primaryKey = primaryKey;
	}

	public List<ColumnInfo> getColumns() {
		return columns;
	}

	public void setColumns(List<ColumnInfo> columns) {
		this.columns = columns;
	}

	@Deprecated
	public Map<String, String> getProperties() {
		return properties;
	}

	@Deprecated
	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

	public Map<String, String> getPropertiesAnColumns() {
		return propertiesAnColumns;
	}

	public void setPropertiesAnColumns(Map<String, String> propertiesAnColumns) {
		this.propertiesAnColumns = propertiesAnColumns;
	}

	public Map<String, String> getInsertPropertiesAnColumns() {
		return insertPropertiesAnColumns;
	}

	public void setInsertPropertiesAnColumns(Map<String, String> insertPropertiesAnColumns) {
		this.insertPropertiesAnColumns = insertPropertiesAnColumns;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public Set<String> getPropTypePackages() {
		return propTypePackages;
	}

	public void setPropTypePackages(Set<String> propTypePackages) {
		this.propTypePackages = propTypePackages;
	}

	public List<PropertyInfo> getAllPropInfo() {
		return allPropInfo;
	}

	public void setAllPropInfo(List<PropertyInfo> allPropInfo) {
		this.allPropInfo = allPropInfo;
	}

	public Map<String, PropertyInfo> getPropInfoMap() {
		return propInfoMap;
	}

	public void setPropInfoMap(Map<String, PropertyInfo> propInfoMap) {
		this.propInfoMap = propInfoMap;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getEntityPackage() {
		if (StringUtils.isNoneBlank(getPrefix())) {
			return entityPackage + Constants.PACKAGE_SEPARATOR + getPrefix();
		}
		return entityPackage;
	}

	public void setEntityPackage(String entityPackage) {
		this.entityPackage = entityPackage;
	}

	public String getDaoPackage() {
		if (StringUtils.isNoneBlank(getPrefix())) {
			return daoPackage + Constants.PACKAGE_SEPARATOR + getPrefix();
		}
		return daoPackage;
	}

	public void setDaoPackage(String daoPackage) {
		this.daoPackage = daoPackage;
	}

	public String getServicePackage() {
		if (StringUtils.isNoneBlank(getPrefix())) {
			return servicePackage + Constants.PACKAGE_SEPARATOR + getPrefix();
		}
		return servicePackage;
	}

	public void setServicePackage(String servicePackage) {
		this.servicePackage = servicePackage;
	}

	public String getControllerPackage() {
		if (StringUtils.isNoneBlank(getPrefix())) {
			return controllerPackage + Constants.PACKAGE_SEPARATOR + getPrefix();
		}
		return controllerPackage;
	}

	public void setControllerPackage(String controllerPackage) {
		this.controllerPackage = controllerPackage;
	}

	public String getServiceImplPackage() {
		if (StringUtils.isNoneBlank(getPrefix())) {
			return serviceImplPackage + Constants.PACKAGE_SEPARATOR + getPrefix();
		}
		return serviceImplPackage;
	}

	public void setServiceImplPackage(String serviceImplPackage) {
		this.serviceImplPackage = serviceImplPackage;
	}

	public String getServiceTestPackage() {
		if (StringUtils.isNoneBlank(getPrefix())) {
			return serviceTestPackage + Constants.PACKAGE_SEPARATOR + getPrefix();
		}
		return serviceTestPackage;
	}

	public void setServiceTestPackage(String serviceTestPackage) {
		this.serviceTestPackage = serviceTestPackage;
	}
}
