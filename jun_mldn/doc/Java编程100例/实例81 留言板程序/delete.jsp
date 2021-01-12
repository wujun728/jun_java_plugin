<html>
<head>
<title>и╬ЁЩаТят</title>
</head>
<body>
<%@ page import="java.sql.*" %>
<%
String p=request.getParameter("delete");
try
{
	Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
	Connection con=DriverManager.getConnection("jdbc:odbc:example","devon","book");
	String str="delete from messages where ID="+ p;
	Statement sm=con.createStatement();
	sm.executeUpdate(str);

	sm.close();
	con.close();
	response.sendRedirect("http://127.0.0.1:8080/jsp-examples/message/viewleave.jsp");
}
catch(Exception e)
{
	out.print(e.getMessage());
}
%>
</body>
</html>
