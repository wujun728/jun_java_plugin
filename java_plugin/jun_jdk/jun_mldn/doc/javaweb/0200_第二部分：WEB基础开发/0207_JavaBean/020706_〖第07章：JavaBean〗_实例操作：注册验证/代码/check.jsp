<%@ page contentType="text/html" pageEncoding="GBK"%>
<html>
<head><title>www.mldnjava.cn£¨MLDN∏ﬂ∂ÀJava≈‡—µ</title></head>
<%
	request.setCharacterEncoding("GBK") ;
%>
<jsp:useBean id="reg" scope="request" class="cn.mldn.lxh.demo.Register"/>
<jsp:setProperty name="reg" property="*"/>
<body>
<%
	if(reg.isValidate()){
%>
		<jsp:forward page="success.jsp"/>
<% 
	} else {
%>
		<jsp:forward page="index.jsp"/>
<%
	}
%>
</body>
</html>