<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>所有用户</title>
</head>
<body>
<h1>你好，世界！！！</h1>
<h4><a href="${pageContext.request.contextPath}/add/user">点击这里添加新用户</a></h4>
<table>
	<tr>
		<th>序号</th>
		<th>ID</th>
		<th>姓名</th>
		<th>地址</th>
		<th>操作</th>
	</tr>
	<c:forEach var="item" items="${ users }" varStatus="status">
	<tr>
	    <td>${status.count}</td>
		<td>${ item.id }</td>
		<td>${ item.name }</td>
		<td>${ item.address }</td>
		<td><a href="${pageContext.request.contextPath}/edit/user/${ item.id }">编辑</a></td>
		<td><a href="${pageContext.request.contextPath}/delete/user/${ item.id }">删除</a></td>
	</tr>
	</c:forEach>
</table>

</body>
</html>