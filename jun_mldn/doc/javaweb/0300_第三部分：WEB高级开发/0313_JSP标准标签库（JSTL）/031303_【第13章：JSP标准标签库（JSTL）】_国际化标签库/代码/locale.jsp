<%@ page contentType="text/html" pageEncoding="GBK"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="fmt" uri="http://www.mldn.cn/jst/fmt"%>
<html>
<head><title>www.mldnjava.cn，MLDN高端Java培训</title></head>
<body>
<%
	pageContext.setAttribute("date",new Date()) ;
%>
<h3>中文日期显示：
	<fmt:setLocale value="zh_CN"/>
	<fmt:formatDate value="${date}"/>
</h3>
<h3>英文日期显示：
	<fmt:setLocale value="en_US"/>
	<fmt:formatDate value="${date}"/>
</h3>
</body>
</html>

