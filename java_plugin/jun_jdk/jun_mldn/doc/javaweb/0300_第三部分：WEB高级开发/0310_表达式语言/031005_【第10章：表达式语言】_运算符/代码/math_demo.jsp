<%@ page contentType="text/html" pageEncoding="GBK"%>
<html>
<head><title>www.mldnjava.cn，MLDN高端Java培训</title></head>
<body>
<%	// 存放的是数字
	pageContext.setAttribute("num1",20) ;
	pageContext.setAttribute("num2",30) ;
%>
<h3>加法操作：${num1 + num2}</h3>
<h3>减法操作：${num1 - num2}</h3>
<h3>乘法操作：${num1 * num2}</h3>
<h3>除法操作：${num1 / num2}和${num1 div num2}</h3>
<h3>取模操作：${num1 % num2}和${num1 mod num2}</h3>
</body>
</html>