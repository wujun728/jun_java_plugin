<%@ page language="java" 
contentType="text/html; charset=GB2312" pageEncoding="GB2312"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head><title>验证码登陆</title> </head>

	<body>
		<form name="form1" method="post" action="">
			<table width="300" height="100" border="1" align="center" 
			cellpadding="0" cellspacing="0" bgcolor=#66ff88>
				<tr><td align="center">	登  陆</td></tr>
				<tr><td align="left">您的昵称：
						<input name="username" type="text">
					</td></tr>
				<tr><td align="left">	验证码：
						<input name="randcode" type="text">
						<IMG src="/book/getImageCode" height=25 width=60>
					</td></tr>
				<tr><td align="center">
						<input type="submit" name="Submit" value="进 入">
					</td></tr>
			</table>
		</form>
	</body>
</html>
