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
		<z:dataGrid dataGridType="datagrid" name="dict" actionUrl="${BASE_PATH}/dict/dictList.do" title="数据字典表" checkbox="false" >
			<z:toolBar title="新增" jsName="Add('${BASE_PATH}/dict/toEdit.do','dict','datagrid',500,300,'DictUpdateForm','${BASE_PATH}/dict/saveOrUpdate.do')" icon="icon-add"></z:toolBar>
			<z:toolBar title="刷新缓存" jsName="ajaxSubmitConfirm('${BASE_PATH}/dict/refresh.do','dict','datagrid','刷新缓存')" icon="icon-reload"></z:toolBar>
			<z:dataGridColumn field="id" title="ID" width="100"  ></z:dataGridColumn>
			<z:dataGridColumn field="name" title="字典名称" width="50" align="left"></z:dataGridColumn>
			<z:dataGridColumn field="code" title="编码" width="50"></z:dataGridColumn>
			<z:dataGridColumn field="type" title="字典类型" width="50" replace="一般_1,数据库表_2,资源文件_3"></z:dataGridColumn>
			<z:dataGridColumn field="tabName" title="表名" width="80"></z:dataGridColumn>
			<z:dataGridColumn field="keyFilde" title="名称字段" width="50"></z:dataGridColumn>
			<z:dataGridColumn field="valueFilde" title="值字段" width="50"></z:dataGridColumn>
			<z:dataGridColumn field="propertiesFile" title="资源文件" width="80"></z:dataGridColumn>
			<z:dataGridColumn field="opt" title="操作" width="100"></z:dataGridColumn>
			<z:dataGridOpt name="删除" jsName="Del('${BASE_PATH}/dict/deleteDict.do?id=&quot;+rowData.id+&quot;','dict','datagrid')"></z:dataGridOpt>
			<z:dataGridOpt name="编辑" jsName="Edit('${BASE_PATH}/dict/toEdit.do?id=&quot;+rowData.id+&quot;','dict','datagrid',500,300,'DictUpdateForm','${BASE_PATH}/dict/saveOrUpdate.do')"></z:dataGridOpt>
			<z:dataGridOpt name="新增字典项" filter="type_1" jsName="OpenPlainWindow('${BASE_PATH}/dict/toDictItem.do?code=&quot;+rowData.code+&quot;',600,400,'字典项管理')"></z:dataGridOpt>
			<z:dataGridOpt name="查看字典项" filter="type_1|2|7|5" jsName="Edit('${BASE_PATH}/dict/toDictItem.do?code=&quot;+rowData.code+&quot;&type=&quot;+rowData.type+&quot;'','dict','datagrid',300,200,'AreaUpdateForm','${BASE_PATH}/area/updateArea.do')"></z:dataGridOpt>
		</z:dataGrid>
	</div>
</div>
</body>
</html>
