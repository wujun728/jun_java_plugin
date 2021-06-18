<%@ page contentType="text/html" pageEncoding="GBK"%>
<%@ page import="org.lxh.listenerdemo.*"%>
<html>
<head><title>www.mldnjava.cn，MLDN高端Java培训</title></head>
<body>
<%
	LoginUser user = new LoginUser("MLDN") ;
	session.setAttribute("info",user) ;	// 直接保存LoginUser对象
%>
</body>
</html>