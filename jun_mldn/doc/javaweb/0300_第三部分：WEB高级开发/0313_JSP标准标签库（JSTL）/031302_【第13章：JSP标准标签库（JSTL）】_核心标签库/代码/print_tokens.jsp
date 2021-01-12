<%@ page contentType="text/html" pageEncoding="GBK"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://www.mldn.cn/jst/core"%>
<html>
<head><title>www.mldnjava.cn，MLDN高端Java培训</title></head>
<body>
	<%
 		String info = "www.MLDNJAVA.cn" ;
		pageContext.setAttribute("ref",info) ;
	%>
	<h3>拆分结果是：
		<c:forTokens items="${ref}" delims="." var="con">
			${con}、
		</c:forTokens></h3>
	<h3>拆分结果是：
		<c:forTokens items="Li:Xing:Hua" delims=":" var="con">
			${con}、
		</c:forTokens></h3>
</body>
</html>