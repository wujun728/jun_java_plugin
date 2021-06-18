<%@ page language="java" contentType="text/html; charset=GB2312" pageEncoding="GB2312"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>获取客户端的IP地址</title>
	</head>

	<body>
		<%
			String realIP = request.getHeader("x-forwarded-for");
			if (realIP != null && realIP.length() != 0) {
				out.print("你的IP地址是：" + realIP);
			} else {
				String ip = request.getRemoteAddr();
				out.print("你的IP地址是：" + ip);
			}
		%>
		<br>
	</body>
</html>
