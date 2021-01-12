<%@ page contentType="text/html" pageEncoding="GBK"%>
<%@ taglib prefix="c" uri="http://www.mldn.cn/jst/core"%>
<html>
<head><title>www.mldnjava.cn，MLDN高端Java培训</title></head>
<body>
	<c:if test="${param.ref=='mldn'}" var="res1" scope="page">
		<h3>欢迎${param.ref}光临</h3>
	</c:if>
	<c:if test="${10<30}" var="res2">
		<h3>10比30小</h3>
	</c:if>
</body>
</html>