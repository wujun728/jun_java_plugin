<%@ page contentType="text/html" pageEncoding="GBK"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="fmt" uri="http://www.mldn.cn/jst/fmt"%>
<html>
<head><title>www.mldnjava.cn，MLDN高端Java培训</title></head>
<body>
<%
	pageContext.setAttribute("dateref",new java.util.Date()) ;
%>
<fmt:timeZone value="HST">
	<fmt:formatDate value="${dateref}" type="both" dateStyle="full" timeStyle="full" var="date"/>
</fmt:timeZone>
<h3>FULL显示日期时间：${date}</h3>
</body>
</html>

