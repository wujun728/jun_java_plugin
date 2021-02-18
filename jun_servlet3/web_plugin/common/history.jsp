<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>

<html>
	<head>
	<title>ÀúÊ·</title>
	<script type="text/javascript">
		var basePath = "<%=basePath%>";
	</script>
	<script type="text/javascript"
		src="<%=basePath%>/template/js/history.js">
	</script>
	</head>
	<body scroll="no">
		<table id="historyTable" cellpadding="0" cellspacing="0"
			style="width: 100%; height: 100%; margin: 0px;">
			<tr>
				<td valign="top">
					 
				</td>
			</tr>
		</table>
	</body>
</html>
