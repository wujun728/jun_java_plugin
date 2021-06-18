<%@ page contentType="text/html" pageEncoding="GBK"%>
<%@ taglib prefix="fn" uri="http://www.mldn.cn/jst/fn"%>
<html>
<head><title>www.mldnjava.cn，MLDN高端Java培训</title></head>
<body>
<%
	pageContext.setAttribute("info","Hello MLDN ,Hello LiXingHua") ;
%>
<h3>查找MLDN：${fn:contains(info,"MLDN")}</h3>
<h3>查找MLDN：${fn:containsIgnoreCase(info,"mldn")}</h3>
<h3>判断开头：${fn:startsWith(info,"Hello")}</h3>
<h3>判断结尾：${fn:endsWith(info,"Hello")}</h3>
<h3>查找位置：${fn:indexOf(info,",")}</h3>
</body>
</html>