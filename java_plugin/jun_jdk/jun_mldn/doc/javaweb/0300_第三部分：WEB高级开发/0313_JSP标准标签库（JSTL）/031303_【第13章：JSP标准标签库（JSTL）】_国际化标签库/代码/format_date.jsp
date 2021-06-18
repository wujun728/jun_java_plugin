<%@ page contentType="text/html" pageEncoding="GBK"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="fmt" uri="http://www.mldn.cn/jst/fmt"%>
<html>
<head><title>www.mldnjava.cn，MLDN高端Java培训</title></head>
<body>
<%
	pageContext.setAttribute("dateref" , new Date()) ;
%>
<fmt:formatDate value="${dateref}" type="both" dateStyle="default" timeStyle="default" var="date"/>
<h3>default显示日期时间：${date}</h3>
<fmt:formatDate value="${dateref}" type="both" dateStyle="short" timeStyle="short" var="date"/>
<h3>short显示日期时间：${date}</h3>
<fmt:formatDate value="${dateref}" type="both" dateStyle="medium" timeStyle="medium" var="date"/>
<h3>medium显示日期时间：${date}</h3>
<fmt:formatDate value="${dateref}" type="both" dateStyle="long" timeStyle="long" var="date"/>
<h3>long显示日期时间：${date}</h3>
<fmt:formatDate value="${dateref}" type="both" dateStyle="full" timeStyle="full" var="date"/>
<h3>full显示日期时间：${date}</h3>
<fmt:formatDate value="${dateref}" type="both" pattern="yyyy年MM月dd日 HH时mm分ss秒SSS毫秒" var="date"/>
<h3>自定义格式显示日期时间：${date}</h3>
</body>
</html>

