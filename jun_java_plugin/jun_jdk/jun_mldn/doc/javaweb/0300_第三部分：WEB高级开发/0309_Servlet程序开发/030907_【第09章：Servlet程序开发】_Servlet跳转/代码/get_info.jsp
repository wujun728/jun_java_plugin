<%@ page contentType="text/html" pageEncoding="GBK"%>
<html>
<head><title>www.mldnjava.cn£¨MLDN∏ﬂ∂ÀJava≈‡—µ</title></head>
<body>
<%	request.setCharacterEncoding("GBK") ;	%>
<h2>sesion Ù–‘£∫<%=session.getAttribute("name")%></h2>
<h2>request Ù–‘£∫<%=request.getAttribute("info")%></h2>
</body>
</html>