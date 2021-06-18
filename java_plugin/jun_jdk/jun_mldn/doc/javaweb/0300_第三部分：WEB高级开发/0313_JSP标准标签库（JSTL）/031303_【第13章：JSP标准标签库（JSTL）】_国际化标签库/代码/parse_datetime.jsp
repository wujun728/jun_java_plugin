<%@ page contentType="text/html" pageEncoding="GBK"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="fmt" uri="http://www.mldn.cn/jst/fmt"%>
<html>
<head><title>www.mldnjava.cn，MLDN高端Java培训</title></head>
<body>
<fmt:parseDate value="2009年7月5日 星期日 上午11时47分45秒 CST" type="both" dateStyle="full" timeStyle="full" var="date"/>
<h3>字符串变为日期：${date}</h3>
<fmt:parseDate value="2009年07月05日 11时47分45秒062毫秒" type="both" pattern="yyyy年MM月dd日 HH时mm分ss秒SSS毫秒" var="date"/>
<h3>字符串变为日期：${date}</h3>
</body>
</html>

