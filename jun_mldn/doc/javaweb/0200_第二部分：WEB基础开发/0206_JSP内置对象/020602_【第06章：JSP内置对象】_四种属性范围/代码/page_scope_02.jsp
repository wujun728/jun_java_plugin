<%@ page contentType="text/html" pageEncoding="GBK"%>
<%@ page import="java.util.*"%>
<html>
<head><title>www.mldnjava.cn，MLDN高端Java培训</title></head>
<body>
<%	// 设置属性
	pageContext.setAttribute("name","李兴华") ;
	pageContext.setAttribute("birthday",new Date()) ;
%>
<jsp:forward page="page_scope_03.jsp"/>
</body>
</html>