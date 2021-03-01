<%@ page language="java" contentType="text/html; charset=GB2312"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>验证码示例</title>
	</head>
	<%
	String contextPath = request.getContextPath();
	%>
	<body>
		<form name="form1" method="post" action="">
			<center>
				<font style="FONT-SIZE:20px;COLOR:#FF6347"><B>登 陆</B> </font>
			</center>
			<table width="50%" height="30%" border="1" align="center"
				cellpadding="0" cellspacing="0" bgcolor=#F8F8FF bordercolor=#A020F0>
				<tr>
					<th>
						<font style="FONT-SIZE:15px;COLOR:#FF8000">用户名：</font>
					</th>
					<td align="left">
						<input name="username" type="text">
					</td>
				</tr>
				<tr>
					<th>
						<font style="FONT-SIZE:15px;COLOR:#FF8000">登陆密码：</font>
					</th>
					<td align="left">
						<input name="password" type="text">
					</td>
				</tr>
				<tr>
					<th>
						<font style="FONT-SIZE:15px;COLOR:#FF8000">输入验证码：</font>
					</th>
					<td align="left">

						<input name="randomNumber" type="text">
						<IMG id="img1" src="<%=contextPath%>/Code_MarkerServlet"
							height=40 width=90>&nbsp;
						<input type="button" name="Submit" value="换一个" onclick="aa()">
					</td>
				</tr>
				<tr>
					<td align="center" colspan="2">
						<input type="submit" name="Submit" value="进 入">
					</td>
				</tr>
			</table>
		</form>
		<script type="text/javascript">
		function aa(){
		     document.getElementById("img1").src="<%=contextPath%>/Code_MarkerServlet";
		     form1.submit();
		}
		</script>
	</body>
</html>
