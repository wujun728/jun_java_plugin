<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="com.java1234.model.Student" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
<%
	request.setCharacterEncoding("utf-8");
	String name=request.getParameter("name");
	String age=request.getParameter("age");
	Student student=new Student();
	student.setName(name);
	student.setAge(Integer.parseInt(age));
%>
<h1>姓名：<%=student.getName() %></h1>
<h1>年龄：<%=student.getAge() %></h1>
</body>
</html>