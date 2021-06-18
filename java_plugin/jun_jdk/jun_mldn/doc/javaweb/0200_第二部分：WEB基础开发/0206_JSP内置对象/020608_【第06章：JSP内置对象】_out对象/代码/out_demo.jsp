<%@ page contentType="text/html" pageEncoding="GBK"%>
<html>
<head><title>www.mldnjava.cn，MLDN高端Java培训</title></head>
<body>
<%
	int buffer = out.getBufferSize() ;
	int avaliable = out.getRemaining() ;
	int use = buffer - avaliable ;
%>
<h3>缓冲区大小：<%=buffer%></h3>
<h3>可用的缓冲区大小：<%=avaliable%></h3>
<h3>使用中的缓冲区大小：<%=use%></h3>
</body>
</html>