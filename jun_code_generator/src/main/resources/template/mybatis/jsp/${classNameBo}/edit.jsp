<#macro mapperEl value>${r"${"}${value}}</#macro>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="${table.classNameFirstLowerBo}/update" class="pageForm required-validate" onsubmit="return validateCallback(this);">
	<div class="pageFormContent" layoutH="56">
		<#list table.columns as column>
		<p>
			<label>${column.columnName}: </label>
			<input type="text" name="${column.columnNameFirstLower}" value="<@mapperEl table.classNameFirstLowerBo+'.'+column.columnNameFirstLower/>"/>
		</p>
		</#list>
	</div>
	<div class="formBar">
		<ul>
			<li><div class="buttonActive"><div class="buttonContent"><button type="submit">Save</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">Close</button></div></div></li>
		</ul>
	</div>
</form>
</div>