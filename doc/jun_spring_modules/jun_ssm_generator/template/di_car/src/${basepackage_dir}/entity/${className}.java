<#assign myParentDir="entity">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
package ${basepackage}.entity;

<#list table.columns as column>
	<#if column.isDateTimeColumn>
import java.text.ParseException;
	<#break/>
	</#if>
</#list>
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import org.springrain.frame.annotation.WhereSQL;
import org.springrain.frame.entity.BaseEntity;
<#include "/copyright_class.include" >
@Table(name="${table.sqlName}")
public class ${className}  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	//alias
	/*
	public static final String TABLE_ALIAS = "${table.tableAlias}";
	<#list table.columns as column>
	public static final String ALIAS_${column.constantName} = "${column.columnAlias}";
	</#list>
    */
	//date formats
	<#list table.columns as column>
	<#if column.isDateTimeColumn>
	//public static final String FORMAT_${column.constantName} = DateUtils.DATETIME_FORMAT;
	</#if>
	</#list>
	
	//columns START
	<#list table.columns as column>
	/**
	 * ${column.columnAlias}
	 */
	private ${column.javaType} ${column.columnNameFirstLower};
	</#list>
	//columns END 数据库字段结束
	
	//concstructor
	<@generateConstructor className/>

	//get and set
	<@generateJavaColumns/>
	@Override
	public String toString() {
		return new StringBuilder()
		<#list table.columns as column>
			.append("${column.columnAlias}[").append(get${column.columnNameFirstUpper}()).append("],")
		</#list>
			.toString();
	}
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
		<#list table.pkColumns as column>
			.append(get${column.columnNameFirstUpper}())
		</#list>
			.toHashCode();
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof ${className} == false){
			return false;
		}
			
		if(this == obj){
			return true;
		}
		
		${className} other = (${className})obj;
		return new EqualsBuilder()
			<#list table.pkColumns as column>
			.append(get${column.columnNameFirstUpper}(),other.get${column.columnNameFirstUpper}())
			</#list>
			.isEquals();
	}
}

	
<#macro generateJavaColumns>
	<#list table.columns as column>
		<#if column.isDateTimeColumn>
		/*
	public String get${column.columnNameFirstLower}String() {
		return DateUtils.convertDate2String(FORMAT_${column.constantName}, get${column.columnNameFirstLower}());
	}
	public void set${column.columnNameFirstLower}String(String value) throws ParseException{
		set${column.columnNameFirstLower}(DateUtils.convertString2Date(FORMAT_${column.constantName},value));
	}*/
	
		</#if>	
		/**
		 * ${column.columnAlias}
		 */
	public void set${column.columnNameFirstUpper}(${column.javaType} value) {
	     <#if column.javaType=="java.lang.String">
		    if(StringUtils.isNotBlank(value)){
			 value=value.trim();
			}
		 </#if>
		this.${column.columnNameFirstLower} = value;
	}
	
	
	
	/**
	 * ${column.columnAlias}
	 */
	<#if column.isPk()>
	@Id
	</#if>
     @WhereSQL(sql="${column.columnNameFirstLower}=:${className}_${column.columnNameFirstLower}")
	public ${column.javaType} get${column.columnNameFirstUpper}() {
		return this.${column.columnNameFirstLower};
	}
	</#list>
</#macro>

<#macro generateJavaOneToMany>
	<#list table.exportedKeys.associatedTables?values as foreignKey>
	<#assign fkSqlTable = foreignKey.sqlTable>
	<#assign fkTable    = fkSqlTable.className>
	<#assign fkPojoClass = fkSqlTable.className>
	<#assign fkPojoClassVar = fkPojoClass?uncap_first>
	
	private Set ${fkPojoClassVar}s = new HashSet(0);
	public void set${fkPojoClass}s(Set<${fkPojoClass}> ${fkPojoClassVar}){
		this.${fkPojoClassVar}s = ${fkPojoClassVar};
	}
	
	public Set<${fkPojoClass}> get${fkPojoClass}s() {
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
	
	

