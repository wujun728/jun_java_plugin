<%@ page contentType="text/html" pageEncoding="GBK"%>
<html>
<head><title>www.mldnjava.cn，MLDN高端Java培训</title></head>
<body>
<%
	Cookie c[] = request.getCookies() ;	// 取得客户端的全部Cookie
	System.out.println(c) ;
	for(int x=0;x<c.length;x++){
%>
		<h3><%=c[x].getName()%> --> <%=c[x].getValue()%></h3>
<%
	}
%>
</body>
</html>