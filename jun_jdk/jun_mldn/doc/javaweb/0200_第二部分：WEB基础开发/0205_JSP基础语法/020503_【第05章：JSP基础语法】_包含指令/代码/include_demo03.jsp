<%@ page contentType="text/html" pageEncoding="GBK"%>
<html>
<head><title>www.mldnjava.cn，MLDN高端Java培训</title></head>
<body>
	<h1>动态包含操作</h1>
	<%
		String username = "LiXingHua" ;
	%>
	<jsp:include page="receive_param.jsp">
		<jsp:param name="name" value="<%=username%>"/>
		<jsp:param name="info" value="www.mldnjava.cn"/>
	</jsp:include>
</body>
</html>