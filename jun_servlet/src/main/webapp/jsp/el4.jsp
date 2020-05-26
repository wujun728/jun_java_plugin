<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	List all=new LinkedList();
	all.add(0,"元素一");
	all.add(1,"元素二");
	all.add(2,"元素三");
	request.setAttribute("all",all);
%>
<h1>${all[0] }</h1>
<h1>${all[1] }</h1>
<h1>${all[2] }</h1>
</body>
</html>