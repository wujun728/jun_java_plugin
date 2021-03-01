<%@ page contentType="text/html" pageEncoding="GBK"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://www.mldn.cn/jst/core"%>
<html>
<head><title>www.mldnjava.cn£¨MLDN∏ﬂ∂ÀJava≈‡—µ</title></head>
<body>
	<%
		Map map = new HashMap() ;
		map.put("mldn","www.MLDNJAVA.cn") ;
		map.put("lxh","LiXingHua") ;
		pageContext.setAttribute("ref",map) ;
	%>
	<c:forEach items="${ref}" var="mem">
		<h3>${mem.key} --> ${mem.value}</h3>
	</c:forEach>
</body>
</html>