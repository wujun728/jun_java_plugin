<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" charset="utf-8">
	try {
		parent.$.messager.progress('close');
	} catch (e) {
	}
</script>
</head>
<body>
	<div>内部错误(Error Code 500)</div>
	<img alt="内部错误" src="<%=basePath%>/css/images/blue_face/bluefaces_05.png">
</body>
</html>
