<%@ page contentType="text/html" pageEncoding="GBK"%>
<html>
<head><title>www.mldnjava.cn£¬MLDN¸ß¶ËJavaÅàÑµ</title></head>
<body>
<%
	pageContext.setAttribute("info","pageÊôÐÔ·¶Î§") ;
	request.setAttribute("info","requestÊôÐÔ·¶Î§") ;
	session.setAttribute("info","sessionÊôÐÔ·¶Î§") ;
	application.setAttribute("info","applicationÊôÐÔ·¶Î§") ;
%>
<h3>PAGEÊôÐÔÄÚÈÝ£º${pageScope.info}</h3>
<h3>REQUESTÊôÐÔÄÚÈÝ£º${requestScope.info}</h3>
<h3>SESSIONÊôÐÔÄÚÈÝ£º${sessionScope.info}</h3>
<h3>APPLICATIONÊôÐÔÄÚÈÝ£º${applicationScope.info}</h3>
</body>
</html>