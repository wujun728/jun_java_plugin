<html>
<head><title>留言保存</title><head>
<body>
<center><h2>谢谢您的留言</h2></center>
<hr>
<h4>您留言如下：</h4>
<%@ page import="java.sql.*"%>
<%
String getDate=new java.util.Date().toString();
String getName=request.getParameter("Name");
String getEmail=request.getParameter("Email");
String getWords=request.getParameter("Message");
String getTopic=request.getParameter("Topic");
if(getName.length()<1)
{
	out.print("请输入姓名");
}
else
if(getEmail.length()<1)
{
	out.print("输入的Email不正确,请重新输入");
}
else
if(getWords.length()<1)
{
	out.print("请输入正确的主题");
}
else
if(getTopic.length()<1)
{
	out.print("请输入留言");
}
else
{
	out.print("<table>");
	out.print("<tr><td align=\"right\">姓名:</td><td>");
	out.print(getName);
	out.print("</td></tr>");
	out.print("<tr><td align=\"rigut\">Email:</td><td>");
	out.print(getEmail);
	out.print("</td></tr>");
	out.print("<tr><td align=\"rigut\">主题:</td><td>");
	out.print(getTopic);
	out.print("</td></tr>");
	out.print("<tr><td valign=\"top\" align=\"rigut\">留言:</td><td>");
	out.print(getWords);
	out.print("</td></tr>");
	out.print("<tr><td align=\"rigut\">日期:</td><td>");
	out.print(getDate);
	out.print("</td></tr></table>");
	try
	{
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		Connection con=DriverManager.getConnection("jdbc:odbc:example","devon","book");
		Statement sm=con.createStatement();
		String str="insert into messages values('"+getName+"','"+getEmail+"','"+getTopic+"','"+getWords+"','"+getDate+"')";
		sm.executeUpdate(str);
		sm.close();
		con.close();
		out.print("<center><h3>留言成功</h3></center>");
	}
	catch(Exception e)
	{
		out.print(e.getMessage());
	}
}
%>
<hr>
<table width="100%" border="0" cellpadding="0" align="center">
<tr>
<td align="right"><a href="message.jsp" title="留言簿">留言簿</a></td>
<td align="center">链接</td>
<td align="left"><a href="viewleave.jsp" title="查看留言">查看留言</a></td>
</tr></table>
</body>
</html>
