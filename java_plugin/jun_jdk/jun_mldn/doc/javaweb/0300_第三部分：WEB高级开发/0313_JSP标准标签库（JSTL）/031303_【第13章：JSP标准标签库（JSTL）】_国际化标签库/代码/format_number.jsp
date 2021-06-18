<%@ page contentType="text/html" pageEncoding="GBK"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="fmt" uri="http://www.mldn.cn/jst/fmt"%>
<html>
<head><title>www.mldnjava.cn，MLDN高端Java培训</title></head>
<body>
<fmt:formatNumber value="351989.356789" maxIntegerDigits="7" maxFractionDigits="3" groupingUsed="true" var="num"/>
<h3>格式化数字：${num}</h3>
<fmt:formatNumber value="351989.356789" pattern="##.###E0" var="num"/>
<h3>科学计数法：${num}</h3>
</body>
</html>

