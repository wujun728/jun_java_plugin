<%@ page contentType="text/html" pageEncoding="GBK"%>
<html>
<head><title>www.mldnjava.cn£¨MLDN∏ﬂ∂ÀJava≈‡—µ</title></head>
<body>
<%
	Cookie c1 = new Cookie("lxh","LiXingHua") ;
	Cookie c2 = new Cookie("mldn","www.MLDNJAVA.cn") ;
	c1.setMaxAge(100) ;
	c2.setMaxAge(100) ;
	response.addCookie(c1) ;
	response.addCookie(c2) ;
%>
</body>
</html>