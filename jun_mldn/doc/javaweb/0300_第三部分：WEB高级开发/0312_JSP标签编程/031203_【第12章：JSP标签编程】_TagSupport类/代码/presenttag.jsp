<%@ page contentType="text/html" pageEncoding="GBK"%>
<%@ taglib prefix="mytag" uri="mldn"%>
<html>
<head><title>www.mldnjava.cn，MLDN高端Java培训</title></head>
<body>
	<%
		String scope = "session" ;	// 假设现在判断的是session范围
		session.setAttribute("username","李兴华") ;
	%>
	<mytag:present name="username" scope="<%=scope%>">
		<h2><%=scope%>范围存在属性，内容是：“${sessionScope.username}”</h2>
	</mytag:present>
	<mytag:present name="allusers" scope="request">
		<h2>request范围存在属性，内容是：“${requestScope.allusers}”</h2>
	</mytag:present>
</body>
</html>