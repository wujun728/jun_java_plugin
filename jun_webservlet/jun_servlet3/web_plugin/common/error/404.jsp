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
	<div>找不到页面(Error Code 404)</div>
	<img alt="找不到页面啦" src="<%=basePath%>/css/images/blue_face/bluefaces_35.png">
</body>
</html>
