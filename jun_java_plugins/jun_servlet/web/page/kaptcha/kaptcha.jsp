<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Kaptcha Example</title>
		<script type="text/javascript">
		function refImg(){
			document.getElementById("Kaptcha").src="<%=basePath%>Kaptcha.jpg?data="+Math.random();
		}
		</script>
	</head>
	<body>
		<table>
			<tr>
				<td><img id="Kaptcha" src="<%=basePath%>Kaptcha.jpg" onclick="refImg()"></td>
				<td valign="top">
					<form method="POST">
						<br>sec code:<input type="text" name="kaptchafield"><br />
						<input type="submit" name="submit">
					</form>
				</td>
			</tr>
		</table>
		<br />
		<%
			String c = (String)session.getAttribute(com.jun.plugin.mybatisplus.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
			String parm = (String) request.getParameter("kaptchafield");
			out.println("Parameter: " + parm + " ? Session Key: " + c + " : ");
			if (c != null && parm != null) {
				if (c.equals(parm)) {
					out.println("<b>true</b>");
				} else {
					out.println("<b>false</b>");
				}
			}
		%>
	</body>
</html>