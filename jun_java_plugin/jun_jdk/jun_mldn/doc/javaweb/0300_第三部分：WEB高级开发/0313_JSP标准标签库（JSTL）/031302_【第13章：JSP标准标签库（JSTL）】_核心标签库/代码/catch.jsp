<%@ page contentType="text/html" pageEncoding="GBK"%>
<%@ taglib prefix="c" uri="http://www.mldn.cn/jst/core"%>
<html>
<head><title>www.mldnjava.cn，MLDN高端Java培训</title></head>
<body>
	<c:catch var="errmsg">
		<%
			int result = 10 / 0 ;
		%>
	</c:catch>
	<h3>异常信息：${errmsg}</h3>
</body>
</html>