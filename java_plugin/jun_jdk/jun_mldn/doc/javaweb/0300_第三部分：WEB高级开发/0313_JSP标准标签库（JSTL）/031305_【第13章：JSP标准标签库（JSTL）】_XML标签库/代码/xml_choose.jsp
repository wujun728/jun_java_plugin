<%@ page contentType="text/html" pageEncoding="GBK"%>
<%@ taglib prefix="c" uri="http://www.mldn.cn/jst/core"%>
<%@ taglib prefix="x" uri="http://www.mldn.cn/jst/x"%>
<html>
<head><title>www.mldnjava.cn，MLDN高端Java培训</title></head>
<body>
	<c:import var="add" url="/xml_jstl/address.xml" charEncoding="GBK"/>
	<x:parse var="addressXml" doc="${add}"/>
	<x:choose>
		<x:when select="$addressXml//name/@id='lxh'">
			<h3>编号是lxh的名称是：<x:out select="$addressXml/addresslist/linkman/name"/></h3>
		</x:when>
		<x:otherwise>
			<h3>啥也不是了！</h3>
		</x:otherwise>
	</x:choose>
</body>
</html>