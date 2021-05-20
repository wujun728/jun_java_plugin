<html>
<head><title>www.mldnjava.cn£¨MLDN∏ﬂ∂ÀJava≈‡—µ</title></head>
<body>
<%
	String username = "LiXingHua" ;
%>
<jsp:forward page="forward_demo02.jsp">
	<jsp:param name="name" value="<%=username%>"/>
	<jsp:param name="info" value="www.mldnjava.cn"/>
</jsp:forward>
</body>
</html>