<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.java1234.model.People" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	People zhangsan=new People();
	zhangsan.setId(1);
	zhangsan.setName("张三");
	zhangsan.setAge(20);
	request.setAttribute("zhangsan",zhangsan);
%>
<h1>编号：${zhangsan.id }</h1>
<h1>姓名：${zhangsan.name }</h1>
<h1>年龄：${zhangsan.age }</h1>
</body>
</html>