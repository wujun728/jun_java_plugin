<%@ page contentType="text/html" pageEncoding="GBK"%>
<html>
<head><title>www.mldnjava.cn，MLDN高端Java培训</title></head>
<body>
<%
	String dbDriver = config.getInitParameter("driver") ;
	String dbUrl = config.getInitParameter("url") ;
%>
<h3>驱动程序：<%=dbDriver%></h3>
<h3>连接地址：<%=dbUrl%></h3>
</body>
</html>