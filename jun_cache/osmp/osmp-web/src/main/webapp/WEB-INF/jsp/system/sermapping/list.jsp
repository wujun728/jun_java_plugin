<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%@include file="/WEB-INF/jsp/pubtag.jsp"%>
</head>
<body>
<script type="text/javascript">
	function dateformat(index,row,ss){
		var temp = zznode.util.dateFormatter(new Date(index),'yyyy-MM-dd hh:mm:ss');
		return temp;
	}
	function refresh(){
	    $.ajax({
	        url:"${BASE_PATH}/config/refreshBundleConfig/dataService.do",
	        type:"post",
	        success:function(data){
	            alert(data);
	        }
	    });
	}
</script>

<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 1px;">
		<z:dataGrid dataGridType="datagrid" name="sermapping" actionUrl="${BASE_PATH}/sermapping/serMappingList.do" title="dataServiceMapping列表" checkbox="false" >
			<z:toolBar title="新增" jsName="Add('${BASE_PATH}/sermapping/toAdd.do','sermapping','datagrid',500,300,'SerMappingAddForm','${BASE_PATH}/sermapping/addSerMapping.do')" icon="icon-add"></z:toolBar>
			<z:toolBar title="刷新配置" jsName="refresh()" icon="icon-reload"></z:toolBar>
			<z:dataGridColumn field="id" title="ID" width="50" ></z:dataGridColumn>
			<z:dataGridColumn field="interfaceName" title="接口名称" width="50"></z:dataGridColumn>
			<z:dataGridColumn field="bundle" title="插件名称" width="200" align="left"></z:dataGridColumn>
			<z:dataGridColumn field="version" title="版本" width="50"></z:dataGridColumn>
			<z:dataGridColumn field="serviceName" title="服务名称" width="50" ></z:dataGridColumn>
			<z:dataGridColumn field="updateTime" title="更新时间" width="50" formatter="dateformat"></z:dataGridColumn>
			<z:dataGridColumn field="opt" title="操作" width="150"></z:dataGridColumn>
			<z:dataGridOpt name="删除" jsName="Del('${BASE_PATH}/sermapping/deleteSerMapping.do?id=&quot;+rowData.id+&quot;','sermapping','datagrid')"></z:dataGridOpt>
			<z:dataGridOpt name="编辑" jsName="Edit('${BASE_PATH}/sermapping/toAdd.do?id=&quot;+rowData.id+&quot;','sermapping','datagrid',500,300,'SerMappingAddForm','${BASE_PATH}/sermapping/updateSerMapping.do')"></z:dataGridOpt>
		</z:dataGrid>
	</div>
</div>
</body>
</html>
