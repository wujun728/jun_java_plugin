<%@ page contentType="text/html" pageEncoding="GBK"%>
<html>
<head><title>www.mldnjava.cn，MLDN高端Java培训</title></head>
<body>
<%
	response.setHeader("refresh","2;URL=login.jsp") ;
	session.invalidate() ;	// 注销，表示当前的session失效
%>
<h3>您已成功退出本系统，两秒后跳转回首页！</h3>
<h3>如果没有跳转，请按<a href="login.jsp">这里</a>！</h3>
</body>
</html>