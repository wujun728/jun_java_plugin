<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%@include file="/WEB-INF/jsp/pubtag.jsp"%>
</head>
<body>
<script type="text/javascript">
</script>

<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 1px;">
		<z:dataGrid dataGridType="datagrid" name="userRoleGrid" actionUrl="${BASE_PATH}/user/getUserRole.do?id=${userId}" title="拥有角色" checkbox="false" showPageList="false">
			<z:dataGridColumn field="name" title="拥有的角色" width="150" ></z:dataGridColumn>
		</z:dataGrid>
	</div>
</div>
</body>
</html>
