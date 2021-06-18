<%@ page contentType="text/html" pageEncoding="GBK"%>
<html>
<head><title>www.mldnjava.cn，MLDN高端Java培训</title></head>
<%
	request.setCharacterEncoding("GBK") ;
%>
<jsp:useBean id="reg" scope="request" class="cn.mldn.lxh.demo.Register"/>
<body>
	用户名：<jsp:getProperty name="reg" property="name"/><br>
	年&nbsp;&nbsp;龄：<jsp:getProperty name="reg" property="age"/><br>
	E-MAIL：<jsp:getProperty name="reg" property="email"/><br>
</body>
</html>