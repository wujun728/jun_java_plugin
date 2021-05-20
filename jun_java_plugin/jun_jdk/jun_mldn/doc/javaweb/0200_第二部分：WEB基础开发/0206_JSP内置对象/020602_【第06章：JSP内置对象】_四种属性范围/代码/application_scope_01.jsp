<%@ page contentType="text/html" pageEncoding="GBK"%>
<%@ page import="java.util.*"%>
<html>
<head><title>www.mldnjava.cn，MLDN高端Java培训</title></head>
<body>
<%	// 设置属性
	application.setAttribute("name","李兴华") ;
	application.setAttribute("birthday",new Date()) ;
%>
<a href="application_scope_02.jsp">通过链接取得属性</a>
</body>
</html>