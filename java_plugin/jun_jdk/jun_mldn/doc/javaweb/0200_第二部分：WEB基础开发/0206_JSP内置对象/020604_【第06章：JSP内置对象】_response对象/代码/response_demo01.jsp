<%@ page contentType="text/html" pageEncoding="GBK"%>
<html>
<head><title>www.mldnjava.cn，MLDN高端Java培训</title></head>
<body>
<%!	// 定义全局变量
	int count = 0 ;
%>
<%
	response.setHeader("refresh","2") ;	// 页面2秒一刷新
%>
<h3>已经访问了<%=count++%>次！</h3>
</body>
</html>