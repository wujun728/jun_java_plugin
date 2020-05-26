<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>设置JDBC连接</h1>
<sql:setDataSource driver="com.mysql.jdbc.Driver" url="jdbc:mysql://localhost:3306/db_jstl" user="root" password="123456" />
<sql:query var="result">
	select * from t_student;
</sql:query>
<h2>总记录数：${result.rowCount }</h2>
<table>
	<tr>
		<th>编号</th>
		<th>学号</th>
		<th>姓名</th>
		<th>出生日期</th>
		<th>性别</th>
	</tr>
	<c:forEach var="student"  items="${result.rows }">
	<tr>
		<td>${student.id }</td>
		<td>${student.stuNo }</td>
		<td>${student.stuName }</td>
		<td>${student.birthday }</td>
		<td>${student.sex }</td>
	</tr>
	</c:forEach>
</table>
</body>
</html>