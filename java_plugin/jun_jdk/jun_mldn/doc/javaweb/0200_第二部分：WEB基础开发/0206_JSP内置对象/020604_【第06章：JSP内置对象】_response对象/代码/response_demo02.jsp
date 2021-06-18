<%@ page contentType="text/html" pageEncoding="GBK"%>
<html>
<head><title>www.mldnjava.cn，MLDN高端Java培训</title></head>
<body>
<h3>3秒后跳转到hello.htm页面，如果没有跳转请按<a href="hello.htm">这里</a>！</h3>
<%
	response.setHeader("refresh","3;URL=hello.htm") ;	// 3秒后跳转到hello.htm页面
%>
</body>
</html>