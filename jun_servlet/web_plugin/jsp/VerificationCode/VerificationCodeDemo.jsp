<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
<!--   	<div style="padding:10px;margin:10px;border:1px dotted gray;"> -->
<!--   		<label>验证码Demo</label> -->
  		<img id="code" src="getCode" onclick="this.src='getCode'" style="cursor: pointer;">
<!--   		<a href="javascript:void(0)" onclick="document.getElementById('code').src='getCode'">看不清,换一张。</a> -->
<!--   	</div> -->
  </body>
</html>
