<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>用户信息</title>
</head>
<body>
    <h1>输入用户信息</h1>
	<form action="${ pageContext.request.contextPath }/save/user" method="post">
		<input type="hidden" name="id" value="${ userForm.id }"/>
		<label>姓名：<input type="text" name="name" value="${ userForm.name }" /></label>
		<label>地址：<input type="text" name="address" value="${ userForm.address }" /></label>
		<input type="submit" value="保存" />
	</form>
</body>
</html>