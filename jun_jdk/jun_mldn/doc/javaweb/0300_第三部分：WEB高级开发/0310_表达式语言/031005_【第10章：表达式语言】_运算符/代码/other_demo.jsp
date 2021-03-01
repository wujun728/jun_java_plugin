<%@ page contentType="text/html" pageEncoding="GBK"%>
<html>
<head><title>www.mldnjava.cn，MLDN高端Java培训</title></head>
<body>
<%	// 存放的是数字
	pageContext.setAttribute("num1",10) ;
	pageContext.setAttribute("num2",20) ;
	pageContext.setAttribute("num3",30) ;
%>
<h3>empty操作：${empty info}</h3>
<h3>三目操作：${num1>num2 ? "大于" : "小于"}</h3>
<h3>括号操作：${num1 * (num2 + num3)}</h3>
</body>
</html>