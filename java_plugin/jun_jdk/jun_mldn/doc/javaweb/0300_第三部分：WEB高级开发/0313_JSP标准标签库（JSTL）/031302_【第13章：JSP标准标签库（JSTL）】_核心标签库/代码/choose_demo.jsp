<%@ page contentType="text/html" pageEncoding="GBK"%>
<%@ taglib prefix="c" uri="http://www.mldn.cn/jst/core"%>
<html>
<head><title>www.mldnjava.cn，MLDN高端Java培训</title></head>
<body>
	<%
		pageContext.setAttribute("num",10) ;
	%>
	<c:choose>
		<c:when test="${num1==10}">
			<h3>num属性的内容是10！</h3>
		</c:when>
		<c:when test="${num1==20}">
			<h3>num属性的内容是20！</h3>
		</c:when>
		<c:otherwise>
			<h3>没有一个条件满足！</h3>
		</c:otherwise>
	</c:choose>
</body>
</html>