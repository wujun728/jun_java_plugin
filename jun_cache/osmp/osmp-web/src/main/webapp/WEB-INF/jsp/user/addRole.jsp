<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%@include file="/WEB-INF/jsp/pubtag.jsp"%>
	<script type="text/javascript">
		function dateformat(index,row,ss){
			var temp = zznode.util.dateFormatter(new Date(index),'yyyy-MM-dd hh:mm:ss');
			return temp;
		}
		function getCheckedRole(){
			var params = {};
			var ids = [];
			var rows = $('#roleOkList').datagrid('getSelections');
			for(var i=0;i<rows.length;i++){
				ids.push(rows[i].id);
			}
			params.ids = ids;
			params.userId = $('#userId').val();
			return params;
		}
	</script>
</head>
<body>
<input type="text" id="userId" value="${userId}" hidden="true"/>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 1px;">
		<z:dataGrid dataGridType="datagrid" name="roleOkList"  actionUrl="${BASE_PATH}/user/roleOkList.do" title="可选角色" checkbox="false" singleSelect="false" showPageList="false">
			<z:dataGridColumn field="id" title="角色ID" width="20" ></z:dataGridColumn>
			<z:dataGridColumn field="name" title="角色名" width="30" align="left"></z:dataGridColumn>
		</z:dataGrid>
	</div>
</div>
</body>
</html>
