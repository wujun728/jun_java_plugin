<html>
<head><title>www.mldnjava.cn£¨MLDN∏ﬂ∂ÀJava≈‡—µ</title></head>
<body>
<%
	int rows = 10 ;
	int cols = 10 ;
	out.println("<table border=\"1\" width=\"100%\">") ;
	for (int x=0;x<rows;x++){
		out.println("<tr>") ;
		for(int y=0;y<cols;y++){
			out.println("<td>" + (x*y) + "</td>") ;
		}
		out.println("</tr>") ;
	}
	out.println("</table>") ;
%>
</body>
</html>