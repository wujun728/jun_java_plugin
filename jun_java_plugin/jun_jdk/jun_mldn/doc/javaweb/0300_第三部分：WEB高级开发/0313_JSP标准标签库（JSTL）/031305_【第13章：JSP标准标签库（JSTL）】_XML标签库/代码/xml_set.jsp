<%@ page contentType="text/html" pageEncoding="GBK"%>
<%@ taglib prefix="c" uri="http://www.mldn.cn/jst/core"%>
<%@ taglib prefix="x" uri="http://www.mldn.cn/jst/x"%>
<html>
<head><title>www.mldnjava.cn£¬MLDN¸ß¶ËJavaÅàÑµ</title></head>
<body>
	<c:import var="add" url="/xml_jstl/address.xml" charEncoding="GBK"/>
	<x:parse var="addressXml" doc="${add}"/>
	<x:set var="nameXml" scope="page" select="$addressXml/addresslist/linkman"/>
	<h3>ĞÕÃû£º<x:out select="$nameXml/name"/></h3>
</body>
</html>