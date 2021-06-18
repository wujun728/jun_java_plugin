<%@ page contentType="text/html" pageEncoding="GBK"%>
<html>
<head><title>www.mldnjava.cn，MLDN高端Java培训</title></head>
<body>
<%
	request.setCharacterEncoding("GBK") ;// 设置的是统一编码
	String param1 = request.getParameter("name") ;
	String param2 = request.getParameter("password") ;
%>
<h3>姓名：<%=param1%></h3>
<h3>密码：<%=param2%></h3>
</body>
</html>