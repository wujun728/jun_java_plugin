<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="${request.getContextPath()}/layout/meta.jsp"></jsp:include>
<jsp:include page="${request.getContextPath()}/layout/easyui.jsp"></jsp:include>
</head>
<body class="easyui-layout">
	<div region="center" border="false" style="padding: 5px;">
		<div>■测试用户：test，密码：test</div>
		<div>■管理员用户：admin，密码：admin</div>
		<div><%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/init.jsp"%></div>
	</div>
</body>
</html>