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
	function fresh(){
		$("#interceptorsGrid").datagrid("reload");
	}
</script>

<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 1px;">
		<z:dataGrid dataGridType="datagrid" name="interceptorsGrid" actionUrl="${BASE_PATH}/interceptors/interceptorList.do" title="拦截器列表" checkbox="false" >
			<z:toolBar title="刷新" jsName="fresh()" icon="icon-reload"></z:toolBar>
			<z:dataGridColumn field="bundle" title="插件名称" width="150" align="left"></z:dataGridColumn>
			<z:dataGridColumn field="version" title="版本" width="50"></z:dataGridColumn>
			<z:dataGridColumn field="name" title="服务名称" width="150" ></z:dataGridColumn>
			<z:dataGridColumn field="state" title="状态" width="30" replace="正常_1,异常_0"></z:dataGridColumn>
			<z:dataGridColumn field="updateTime" title="更新时间" width="150" formatter="dateformat"></z:dataGridColumn>
			<z:dataGridColumn field="mark" title="备注" width="200" align="left"></z:dataGridColumn>
			<z:dataGridColumn field="loadIP" title="服务器IP" width="150"></z:dataGridColumn>
		</z:dataGrid>
	</div>
</div>
</body>
</html>
