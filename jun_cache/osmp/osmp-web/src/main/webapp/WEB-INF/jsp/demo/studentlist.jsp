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
		<z:dataGrid dataGridType="datagrid" name="student" actionUrl="${BASE_PATH}/demo/studentList.do" title="DEMO 学生表" checkbox="false" showPageList="false">
			<z:toolBar title="新增" jsName="Add('${BASE_PATH}/demo/toAdd.do','area','datagrid',300,200,'AreaAddForm','${BASE_PATH}/demo/addArea.do')" icon="icon-add"></z:toolBar>
			<z:dataGridColumn field="id" title="学生ID" width="20" ></z:dataGridColumn>
			<z:dataGridColumn field="name" title="学生名称" width="150" align="left"></z:dataGridColumn>
			<z:dataGridColumn field="age" title="年龄" width="30"></z:dataGridColumn>
			<z:dataGridColumn field="school" title="学校" width="30"></z:dataGridColumn>
			<z:dataGridColumn field="remark" title="描述" width="30"></z:dataGridColumn>
			<z:dataGridColumn field="opt" title="操作" width="100"></z:dataGridColumn>
			<z:dataGridOpt name="删除" jsName="Del('${BASE_PATH}/area/deleteArea.do?id=&quot;+rowData.id+&quot;','area','treegrid')"></z:dataGridOpt>
			<z:dataGridOpt name="编辑" jsName="Edit('${BASE_PATH}/area/toEdit.do?id=&quot;+rowData.id+&quot;','area','treegrid',300,200,'AreaUpdateForm','${BASE_PATH}/area/updateArea.do')"></z:dataGridOpt>
		</z:dataGrid>
	</div>
</div>
</body>
</html>
