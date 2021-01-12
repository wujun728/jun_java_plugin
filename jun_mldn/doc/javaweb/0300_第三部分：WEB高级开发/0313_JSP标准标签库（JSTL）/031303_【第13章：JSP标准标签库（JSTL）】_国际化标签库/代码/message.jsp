<%@ page contentType="text/html" pageEncoding="GBK"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="fmt" uri="http://www.mldn.cn/jst/fmt"%>
<html>
<head><title>www.mldnjava.cn，MLDN高端Java培训</title></head>
<body>
<fmt:bundle basename="Message">
	<fmt:message key="name" var="nameref"/>
</fmt:bundle>
<h3>姓名：${nameref}</h3>
<fmt:bundle basename="Message">
	<fmt:message key="info" var="inforef">
		<fmt:param value="MLDN"/>
	</fmt:message>
</fmt:bundle>
<h3>信息：${inforef}</h3>
</body>
</html>

