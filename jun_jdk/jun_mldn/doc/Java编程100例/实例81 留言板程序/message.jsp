<html>
<head>
<title>留言簿</title>
</head>
<BODY  bgColor=#00f068>
<center>
<%@page contentType="text/html;charset=gb2312"%>
<table width="60%" border="0" cellpadding="0">
<tr>
 <td align="center"><a href="viewleave.jsp" title="查看留言">查看留言</a></td>
</tr>
</table>
<hr>
<form name="myform" action="leave.jsp" method="POST">
<table width="60%" border="0" cellpadding="0">
<tr>
 <td align="left"><b>姓名：</b></td>
 <td align="left"><input type="text" name="Name" SIZE="20" value=""></td>
</tr>
<tr>
 <td align="left"><b>Email地址：</b></td>
 <td align="left"><input type="text" name="Email" SIZE="20" value=""></td>
</tr>
<tr>
 <td align="left"><b>主题：</b></td>
 <td align="left" colspan="3"><input type="text" name="Topic" SIZE="30" value=""></td>
</tr>
<tr>
 <td colspan="4" align="left"><textarea name="Message" rows="8" cols="50"></textarea></td>
</tr>
</table>
<tr>
<td align="center"><input type="submit" name="leave" value="给我留言"></a></td>
</tr>
</form>
</center>
</body>
</html>
