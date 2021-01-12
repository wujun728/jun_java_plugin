<%@ page contentType="text/html" pageEncoding="GBK"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://www.mldn.cn/jst/core"%>
<html>
<head><title>www.mldnjava.cn，MLDN高端Java培训</title></head>
<body>
	<%
		List all = new ArrayList() ;
		all.add("MLDN") ;
		all.add("LiXingHua") ;
		all.add("www.MLDNJAVA.cn") ;
		pageContext.setAttribute("ref",all) ;
	%>
	<h3>输出全部：
	<c:forEach items="${ref}" var="mem">
		${mem}、
	</c:forEach></h3>
</body>
</html>