<%@ page contentType="text/html" pageEncoding="GBK"%>
<html>
<head><title>www.mldnjava.cn，MLDN高端Java培训</title></head>
<body>
<%	System.out.println("============ forward跳转之前 =========") ;%>
	<%	response.sendRedirect("hello.htm") ;	%>
<%	System.out.println("============ forward跳转之后=========") ;%>
</body>
</html>