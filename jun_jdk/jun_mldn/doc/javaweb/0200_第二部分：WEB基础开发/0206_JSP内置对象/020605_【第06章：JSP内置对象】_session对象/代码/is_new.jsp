<%@ page contentType="text/html" pageEncoding="GBK"%>
<html>
<head><title>www.mldnjava.cn，MLDN高端Java培训</title></head>
<body>
<%
	if(session.isNew()){
%>
		<h3>欢迎新用户光临！</h3>
<%
	} else {
%>
		<h3>您已经是老用户了！</h3>
<%
	}
%>
</body>
</html>