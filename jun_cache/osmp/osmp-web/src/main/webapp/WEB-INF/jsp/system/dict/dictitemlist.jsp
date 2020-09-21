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
		<z:dataGrid dataGridType="datagrid" name="dictitem" actionUrl="${BASE_PATH}/dict/dictitemList.do?code=${code}" title="数据字典表" checkbox="false" showPageList="false">
			<z:toolBar title="新增" jsName="Add('${BASE_PATH}/dict/toDictItemEdit.do?code=${code}','dictitem','datagrid',500,300,'DictItemUpdateForm','${BASE_PATH}/dict/dictItemsave.do')" icon="icon-add"></z:toolBar>
			<z:dataGridColumn field="id" title="ID" width="100"  ></z:dataGridColumn>
			<z:dataGridColumn field="parentCode" title="字典名称" width="50" align="left"></z:dataGridColumn>
			<z:dataGridColumn field="name" title="名称" width="50"></z:dataGridColumn>
			<z:dataGridColumn field="code" title="编码" width="50" ></z:dataGridColumn>
			<z:dataGridColumn field="opt" title="操作" width="100"></z:dataGridColumn>
			<z:dataGridOpt name="删除" jsName="Del('${BASE_PATH}/dict/deleteDictItem.do?id=&quot;+rowData.id+&quot;','dictitem','datagrid')"></z:dataGridOpt>
		</z:dataGrid>
	</div>
</div>
</body>
</html>
