<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
	String userCode = request.getAttribute("userCode").toString();
%>
<html>
  <head>
    <title>Ê×Ò³</title>
	<link rel="stylesheet" href="/ptxtag/css/default.css"/>
    <script type="text/javascript">
      var basePath = "<%=basePath%>";
    </script>
    <script type="text/javascript" src="<%=basePath%>/template/js/index.js"></script>
  </head>
  <body scroll="no">
  </body>
</html>
