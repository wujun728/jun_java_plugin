<%@ page contentType="text/html" pageEncoding="GBK"%>
<html>
<head><title>www.mldnjava.cn，MLDN高端Java培训</title></head>
<body>
<%
	request.setCharacterEncoding("GBK") ;// 设置的是统一编码
	//String content = new // String(request.getParameter("info").getBytes("ISO8859-1")) ;
	String content = request.getParameter("info") ;
%>
<h2><%=content%></h2>
</body>
</html>