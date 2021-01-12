<%@ page contentType="text/html" pageEncoding="GBK"%>
<%@ page import="java.util.*"%>
<html>
<head><title>www.mldnjava.cn，MLDN高端Java培训</title></head>
<body>
<%	// 设置属性
	pageContext.setAttribute("name","李兴华") ;
	pageContext.setAttribute("birthday",new Date()) ;
%>
<%
	String username = (String) pageContext.getAttribute("name") ;
	Date userbirthday = (Date)pageContext.getAttribute("birthday") ;
%>
<h2>姓名：<%=username%></h2>
<h2>生日：<%=userbirthday%></h2>
</body>
</html>