package ${basepackage}.domain;
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
import java.io.Serializable;

public class ${className} implements Serializable{
	
	<#list table.columns as column>
	private ${column.possibleShortJavaType ${column.columnNameFirstLower};
	</#list>

<@generateJavaColumns/>

<#macro generateJavaColumns>
	<#list table.columns as column>
	<#assign _javaType = column.possibleShortJavaType>

	<#if (_javaType == 'Boolean')>
	public ${_javaType} is${column.columnName}() {
		return this.${column.columnNameFirstLower};
	}
	<#else>
	public ${_javaType} get${column.columnName}() {
		return this.${column.columnNameFirstLower};
	}
	</#if>
	public void set${column.columnName}(${_javaType} ${column.columnNameFirstLower}) {
		this.${column.columnNameFirstLower} = ${column.columnNameFirstLower};
	}
	</#list>
</#macro>
}
