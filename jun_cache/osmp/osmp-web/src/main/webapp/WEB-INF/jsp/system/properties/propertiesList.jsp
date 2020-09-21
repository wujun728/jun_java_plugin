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
		<z:dataGrid dataGridType="datagrid" name="properties" actionUrl="${BASE_PATH}/properties/proList.do" title="资源信息表" checkbox="false" >
			<z:toolBar title="新增" jsName="Add('${BASE_PATH}/properties/toEdit.do','properties','datagrid',500,300,'UpdateForm','${BASE_PATH}/properties/saveOrUpdate.do')" icon="icon-add"></z:toolBar>
			<z:dataGridColumn field="id" title="ID" width="60"  ></z:dataGridColumn>
			<z:dataGridColumn field="name" title="名称" width="50" align="left"></z:dataGridColumn>
			<z:dataGridColumn field="prokey" title="KEY" width="50"></z:dataGridColumn>
			<z:dataGridColumn field="provalue" title="VALUE" width="50" ></z:dataGridColumn>
			<z:dataGridColumn field="remark" title="备注" width="100" ></z:dataGridColumn>
			<z:dataGridColumn field="opt" title="操作" width="100"></z:dataGridColumn>
			<z:dataGridOpt name="删除" jsName="Del('${BASE_PATH}/properties/deletePro.do?id=&quot;+rowData.id+&quot;','properties','datagrid')"></z:dataGridOpt>
			<z:dataGridOpt name="编辑" jsName="Edit('${BASE_PATH}/properties/toEdit.do?id=&quot;+rowData.id+&quot;','properties','datagrid',500,300,'UpdateForm','${BASE_PATH}/properties/saveOrUpdate.do')"></z:dataGridOpt>
		</z:dataGrid>
	</div>
</div>
</body>
</html>
