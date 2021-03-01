<%@ page contentType="text/html" pageEncoding="GBK"%>
<html>
<head><title>www.mldnjava.cn，MLDN高端Java培训</title></head>
<body>
<%	// 如果已经设置过了session属性，则肯定不为空
	if(session.getAttribute("userid")!=null){
%>
		<h3>欢迎<%=session.getAttribute("userid")%>光临本系统，<a href="logout.jsp">注销</a></h3>
<%
	} else {	// 没有session，则应该给出提示，先去登陆
%>
		<h3>请先进行系统的<a href="login.jsp">登陆</a>！</h3>
<%
	}
%>
</body>
</html>