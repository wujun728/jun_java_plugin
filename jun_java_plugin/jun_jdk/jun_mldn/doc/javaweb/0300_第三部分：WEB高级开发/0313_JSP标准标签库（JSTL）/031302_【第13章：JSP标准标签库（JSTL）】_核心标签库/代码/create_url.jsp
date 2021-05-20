<%@ page contentType="text/html" pageEncoding="GBK"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://www.mldn.cn/jst/core"%>
<html>
<head><title>www.mldnjava.cn，MLDN高端Java培训</title></head>
<body>
	<c:url value="http://www.mldnjava.cn" var="urlinfo">
		<c:param name="author" value="李兴华"/>
		<c:param name="logo" value="mldn"/>
	</c:url>
	<a href="${urlinfo}">新的地址</a>
</body>
</html>