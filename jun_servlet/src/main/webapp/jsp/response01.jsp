<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
<%
	// 每隔一秒刷新一次页面
	response.setHeader("refresh","1");
	// 获取当前时间
	Date myDate=new Date();
%>
当前时间：<%= myDate.toLocaleString() %>
</body>
</html>