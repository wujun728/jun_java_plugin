<%@ page contentType="text/html" pageEncoding="GBK"%>
<html>
<head><title>www.mldnjava.cn，MLDN高端Java培训</title></head>
<body>
<%	// 假设以下的设置属性操作是在Servlet之中完成
	request.setAttribute("info","www.MLDNJAVA.cn") ;	// 设置一个request属性范围
%>
<%
	if(request.getAttribute("info") != null){	// 现在有属性存在
%>
		<h3><%=request.getAttribute("info")%></h3>
<%
	}
%>
</body>
</html>