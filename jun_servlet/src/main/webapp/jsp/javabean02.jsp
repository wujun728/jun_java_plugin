<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
<jsp:useBean id="student" scope="page" class="com.java1234.model.Student"/>
<%
	student.setName("王二小2");
	student.setAge(12);
%>
<h1>姓名：<%=student.getName() %></h1>
<h1>年龄：<%=student.getAge() %></h1>
</body>
</html>