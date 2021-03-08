<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

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

		<title>查询用户信息</title>


	</head>

	<body>
		<form action="deleteAction">
			请输入要删除的ID列：
			<input type="text" name="user.id" />
			<input type="submit" value="删除" />
		</form>
		
		<form action="updateAction">
			请输出ID：
			<input type="text" name="user.id" />
			请输入账号:
			<input type="text" name="user.name" />
			请输入修改密码：
			<input type="text" name="user.password" />
			<input type="submit" value="修改" />
		</form>
		<form action="addAction">
			请输入账号：
			<input type="text" name="user.name" />
			请输入密码:
			<input type="password" name="user.password" />
			<input type="submit" value="添加" />
		</form>
		ID：${user.id}</br>
		用户名：${user.name}</br>
		密码：${password}</br>
	</body>
</html>
