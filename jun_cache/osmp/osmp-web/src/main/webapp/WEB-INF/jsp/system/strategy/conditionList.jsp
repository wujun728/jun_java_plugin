<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%@include file="/WEB-INF/jsp/pubtag.jsp"%>
</head>
<body>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 1px;">
		<z:dataGrid dataGridType="datagrid" name="conditionList" actionUrl="${BASE_PATH}/strategy/conditionList.do?id=${id}" title="满足条件" checkbox="false" showPageList="false">
			<z:toolBar title="新增" jsName="Add('${BASE_PATH}/strategy/toConditionAdd.do?id=${id}','conditionList','datagrid',500,300,'conditionAddForm','${BASE_PATH}/strategy/conditionAdd.do')" icon="icon-add"></z:toolBar>
			<z:dataGridColumn field="id" title="ID" width="130"  ></z:dataGridColumn>
			<z:dataGridColumn field="type" title="判断类型" width="40"></z:dataGridColumn>
			<z:dataGridColumn field="typeRemark" title="类型备注" width="50"></z:dataGridColumn>
			<z:dataGridColumn field="key" title="关键字" width="40" align="left"></z:dataGridColumn>
			<z:dataGridColumn field="condition" title="条件" width="20" ></z:dataGridColumn>
			<z:dataGridColumn field="value" title="值" width="40" ></z:dataGridColumn>
			<z:dataGridColumn field="opt" title="操作" width="50"></z:dataGridColumn>
			<z:dataGridOpt name="删除" jsName="Del('${BASE_PATH}/strategy/deleteCondition.do?id=&quot;+rowData.id+&quot;','conditionList','datagrid')"></z:dataGridOpt>
		</z:dataGrid>
	</div>
</div>
</body>
</html>
