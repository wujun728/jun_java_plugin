<%@ page contentType="text/html" pageEncoding="GBK"%>
<%@ taglib prefix="c" uri="http://www.mldn.cn/jst/core"%>
<%@ taglib prefix="x" uri="http://www.mldn.cn/jst/x"%>
<html>
<head><title>www.mldnjava.cn£¨MLDN∏ﬂ∂ÀJava≈‡—µ</title></head>
<body>
	<c:import var="add" url="/xml_jstl/alladdress.xml" charEncoding="GBK"/>
	<x:parse var="addressXml" doc="${add}"/>
	<x:forEach select="$addressXml//linkman" var="linkman">
		<h3>–’√˚£∫<x:out select="name"/>£®±‡∫≈£∫<x:out select="name/@id"/>£©
	</x:forEach>
</body>
</html>