<html>
<head><title>www.mldnjava.cn£¨MLDN∏ﬂ∂ÀJava≈‡—µ</title></head>
<body>
<%
	int rows = 0 ;
	int cols = 0 ;
	try{
		rows = Integer.parseInt(request.getParameter("row")) ;
		cols = Integer.parseInt(request.getParameter("col")) ;
	}catch(Exception e){}
%>
<table border="1" width="100%">
<%
	for (int x=0;x<rows;x++){
%>
		<tr>
<%
		for(int y=0;y<cols;y++){
%>
			<td bgcolor="#00CC33"><%=x*y%></td>
<%
		}
%>
		</tr>
<%
	}
%>
</table>
</body>
</html>