<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>注册成功</title>
  </head>
  <body>
    <h1 align="center"><font color="#ff0000">注册成功</font></h1>
    <div align="center"><a href ="login.jsp">返回登录页面</a><br><br></div><br>
  </body>
</html>
