<#assign className = table.className>   
<#assign classNameLowerCase = table.classNameLowerCase>   
<#assign classNameLower = className?uncap_first>   
package ${basepackage}.dao;

import java.io.Serializable;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.baobaotao.domain.User;

import ${basepackage}.domain.${className};

@Repository
public class ${className}Dao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public ${className} get${className}byId(final Integer Id)
	{
		String sqlStr = "select "
		<#list table.columns as column>
			<#if column_has_next>
				+" t.${column.sqlName}, "
			</#if>
			<#if !column_has_next>
				+" t.${column.sqlName} "
			</#if>
		</#list>
			+" from ${table.sqlName} t where 1=1 "
		<#list table.columns as column>
		  	<#if column.pk>
		  	+ " and t.${column.sqlName} = ? ";
		  	</#if>
		</#list>
	final ${className} ${classNameLowerCase} = new ${className}();
	jdbcTemplate.query(sqlStr, new Object[] { Id },
			new RowCallbackHandler() {
				public void processRow(ResultSet rs) throws SQLException {
				<#list table.columns as column>
					<#if column.isStringColumn>
						${classNameLowerCase}.set${column.columnName}(rs.getString("${column.sqlName}"));
					</#if>
					<#if column.isDateTimeColumn>
						${classNameLowerCase}.set${column.columnName}(rs.getDate("${column.sqlName}"));
					</#if>
					<#if column.javaType=("java.lang.Integer")>
						${classNameLowerCase}.set${column.columnName}(rs.getInt("${column.sqlName}"));
					</#if>
					<#if column.javaType=("java.lang.Double")>
							${classNameLowerCase}.set${column.columnName}(rs.getDouble("${column.sqlName}"));
					</#if>
					<#if column.javaType=("java.lang.Float")>
						${classNameLowerCase}.set${column.columnName}(rs.getFloat("${column.sqlName}"));
					</#if>
					<#if column.javaType=("java.lang.Decimal")>
						${classNameLowerCase}.set${column.columnName}(rs.getDouble("${column.sqlName}"));
					</#if>
				</#list>
				}
			});
	return ${classNameLowerCase};
	}
	
	public void update${className}(${className} ${classNameLowerCase}) {
		String sqlStr = " UPDATE ${table.sqlName} set "
		<#list table.columns as column>
			<#if !column.pk && column_has_next>
				+"  ${column.sqlName} = ?,"
			</#if>
			<#if !column.pk && !column_has_next>
				+"  ${column.sqlName} = ?"
			</#if>
		</#list>
				+ " WHERE "
		<#list table.columns as column>
			<#if column.pk>
				+" ${column.sqlName} = ? ";
			</#if>
		</#list>
		jdbcTemplate.update(sqlStr, new Object[] { 
			<#list table.columns as column>
				<#if !column.pk>
					${classNameLowerCase}.get${column.columnName}(),
				</#if>
			</#list>
			<#list table.columns as column>
				<#if column.pk>
					${classNameLowerCase}.get${column.columnName}()
				</#if>
			</#list>
				});
	}
	
	public void insert${className}(${className} ${classNameLowerCase}){
		String sqlStr = " insert into ${table.sqlName}( "
		<#list table.columns as column>
			<#if !column.pk && column_has_next>
				+" ${column.sqlName} ,"
			</#if>
			<#if !column.pk && !column_has_next>
				+" ${column.sqlName} "
			</#if>
		</#list>
		+" )values ( "
		<#list table.columns as column>
			<#if !column.pk && column_has_next>
				+" ? ,"
			</#if>
			<#if !column.pk && !column_has_next>
				+" ? "
			</#if>
		</#list>
		+"  )";
		jdbcTemplate.update(sqlStr, new Object[] { 
				<#list table.columns as column>
					<#if !column.pk>
						${classNameLowerCase}.get${column.columnName}(),
					</#if>
				</#list>
					});
	}
	public void delete${className}(${className} ${classNameLowerCase}){
		String sqlStr = "  delete from  ${table.sqlName} where  "
		<#list table.columns as column>
			<#if column.pk>
				+ " ${column.sqlName}=? ";
			</#if>
		</#list>
		jdbcTemplate.update(sqlStr, new Object[] { 
				<#list table.columns as column>
					<#if column.pk>
						${classNameLowerCase}.get${column.columnName}()
					</#if>
				</#list>
					});
	}
	

}
