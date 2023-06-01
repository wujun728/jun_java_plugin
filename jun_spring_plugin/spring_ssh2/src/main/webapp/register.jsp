<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="struts"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>请注册</title>
	</head>
	<body>
		<center>
			<form action="registerAction">
				账号
				<input type="text" name="user.name" />
				<br>
				密码
				<input type="password" name="user.password" />
				<br>
				<input type="submit" value="注册" />
				<input type="reset" value="重置" />
			</form>
		</center>
	</body>
</html>
