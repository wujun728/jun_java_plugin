<%@ page contentType="text/html" pageEncoding="GBK"%>
<html>
<head><title>www.mldnjava.cn£¨MLDN∏ﬂ∂ÀJava≈‡—µ</title></head>
<body>
<form action="login.jsp" method="post">
	”√ªßID£∫<input type="text" name="userid">
	<input type="submit" value="µ«¬Ω">
</form>
<%
	String userid = request.getParameter("userid") ;
	if(!(userid==null || "".equals(userid))){
		session.setAttribute("userid",userid) ;
		response.sendRedirect("list.jsp") ;
	}
%>
</body>
</html>