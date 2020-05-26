<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
<%
	// 取值
	String name=(String)request.getAttribute("name");
	int age=(Integer)request.getAttribute("age");
	// 获取头信息
	Enumeration enu=request.getHeaderNames();
	while(enu.hasMoreElements()){
		String headerName=(String)enu.nextElement();
		String headerValue=request.getHeader(headerName);
%>
	<h4><%=headerName %>&nbsp;<%=headerValue %></h4>
<%
	}
%>
<font>姓名：<%=name %></font>
<font>年龄：<%=age %></font>
</body>
</html>