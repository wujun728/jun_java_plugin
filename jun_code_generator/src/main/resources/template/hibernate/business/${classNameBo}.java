<#include "/java_copyright.include"/>
<#include "/macro.include"/>
package ${basepackage}.business;

import dwz.persistence.beans.JzKmInitBalance;
import ${basepackage}.business.AbstractBusinessObject;
import ${basepackage}.beans.${table.className};
<#assign po = table.classNameFirstLower>

public class ${table.classNameBo} extends AbstractBusinessObject{
	private static final long serialVersionUID = 1L;
	private ${table.className} ${po};
	
	/* generateConstructor */
	public ${table.classNameBo}(){
		this.${po} = new ${table.className}();
	}
	public ${table.classNameBo}(${table.className} ${po}){
		this.${po} = ${po};
	}
	public ${table.className} get${table.className}(){
		return this.${po};
	}
	
<@generateJavaColumns/>
<@generateJavaOneToMany/>
<@generateJavaManyToOne/>

}

<#macro generateJavaColumns>
	<#list table.columns as column>
	<#assign _javaType = column.possibleShortJavaType>
	<#assign _javaTypeBo = column.primitiveJavaType>
	
	<#if (column.pk)>
	public ${_javaType} get${column.columnName}() {
		return this.${po}.get${column.columnName}();
	}
	public void set${column.columnName}(${_javaType} ${column.columnNameFirstLower}) {
		this.${po}.set${column.columnName}(${column.columnNameFirstLower});
	}
	
	<#elseif (_javaType == 'Boolean')>
	public ${_javaTypeBo} is${column.columnName}() {
		${_javaType} value = this.${po}.get${column.columnName}();
		return value != null ? value : false;
	}
	public void set${column.columnName}(${_javaTypeBo} ${column.columnNameFirstLower}) {
		this.${po}.set${column.columnName}(${column.columnNameFirstLower});
	}
	
	<#elseif (_javaType == 'Integer' || _javaType == 'Double' || _javaType == 'Float')>
	public ${_javaType} get${column.columnName}() {
		return this.${po}.get${column.columnName}();
	}
	public void set${column.columnName}(${_javaType} ${column.columnNameFirstLower}) {
		this.${po}.set${column.columnName}(${column.columnNameFirstLower});
	}
	
	<#else>
	public ${_javaTypeBo} get${column.columnName}() {
		return this.${po}.get${column.columnName}();
	}
	public void set${column.columnName}(${_javaTypeBo} ${column.columnNameFirstLower}) {
		this.${po}.set${column.columnName}(${column.columnNameFirstLower});
	}
	</#if>
	</#list>
</#macro>

<#macro generateJavaOneToMany>
	<#list table.exportedKeys.associatedTables?values as foreignKey>
	<#assign fkSqlTable = foreignKey.sqlTable>
	<#assign fkTable    = fkSqlTable.className>
	<#assign fkPojoClass = fkSqlTable.className>
	<#assign fkPojoClassVar = fkPojoClass?uncap_first>
	
	private Set ${fkPojoClassVar}s = new HashSet(0);
	public void set${fkPojoClass}s(Set ${fkPojoClassVar}){
		this.${fkPojoClassVar}s = ${fkPojoClassVar};
	}
	
	public Set get${fkPojoClass}s() {
		return ${fkPojoClassVar}s;
	}
	</#list>
</#macro>

<#macro generateJavaManyToOne>
	<#list table.importedKeys.associatedTables?values as foreignKey>
	<#assign fkSqlTable = foreignKey.sqlTable>
	<#assign fkTable    = fkSqlTable.className>
	<#assign fkPojoClass = fkSqlTable.className>
	<#assign fkPojoClassVar = fkPojoClass?uncap_first>
	
	private ${fkPojoClass} ${fkPojoClassVar};
	
	public void set${fkPojoClass}(${fkPojoClass} ${fkPojoClassVar}){
		this.${fkPojoClassVar} = ${fkPojoClassVar};
	}
	
	public ${fkPojoClass} get${fkPojoClass}() {
		return ${fkPojoClassVar};
	}
	</#list>
</#macro>

