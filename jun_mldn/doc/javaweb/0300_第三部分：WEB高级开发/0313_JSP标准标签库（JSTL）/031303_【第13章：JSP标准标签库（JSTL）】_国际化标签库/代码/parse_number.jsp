<%@ page contentType="text/html" pageEncoding="GBK"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="fmt" uri="http://www.mldn.cn/jst/fmt"%>
<html>
<head><title>www.mldnjava.cn，MLDN高端Java培训</title></head>
<body>
<fmt:parseNumber value="3,531,989.357" var="num"/>
<h3>反格式化数字：${num}</h3>
<fmt:parseNumber value="3.532E6" pattern="##.###E0" var="num"/>
<h3>反科学计数法：${num}</h3>
<fmt:parseNumber value="3.5%" pattern="00%" var="num"/>
<h3>反百分比：${num}</h3>
</body>
</html>

