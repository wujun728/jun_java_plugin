<%@ page contentType="text/html" pageEncoding="GBK"%>
<%@ taglib prefix="c" uri="http://www.mldn.cn/jst/core"%>
<html>
<head><title>www.mldnjava.cn，MLDN高端Java培训</title></head>
<body>
	<%
		String info[] = {"MLDN","LiXingHua","www.MLDNJAVA.cn"} ;
		pageContext.setAttribute("ref",info) ;
	%>
	<h3>输出全部：
	<c:forEach items="${ref}" var="mem">
		${mem}、
	</c:forEach></h3>
	<h3>输出全部（间隔为2）：
	<c:forEach items="${ref}" var="mem" step="2">
		${mem}、
	</c:forEach></h3>
	<h3>输出前两个：
	<c:forEach items="${ref}" var="mem" begin="0" end="1">
		${mem}、
	</c:forEach></h3>
</body>
</html>