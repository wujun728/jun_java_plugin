<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
<%
	// 设置两个request范围的数据 key-> value
	request.setAttribute("name","request王二小");
	request.setAttribute("age",12);
%>
<jsp:forward page="requestTarget.jsp"></jsp:forward>
</body>
</html>