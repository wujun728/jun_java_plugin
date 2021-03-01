<%@ page contentType="text/html" pageEncoding="GBK"%>
<html>
<head><title>www.mldnjava.cn，MLDN高端Java培训</title></head>
<body>
<%	// 存放的是数字
	pageContext.setAttribute("flagA",true) ;
	pageContext.setAttribute("flagB",false) ;
%>
<h3>与操作：${flagA && flagB} 和 ${flagA and flagB}</h3>
<h3>或操作：${flagA || flagB} 和 ${flagA or flagB}</h3>
<h3>非操作：${!flagA} 和 ${not flagB}</h3>
</body>
</html>