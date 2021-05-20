<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>请登录</title>

  </head>
  
  <body>
   <form action="loginAction">
    			账户<input type="text" name="user.name"/><br>
    			密码 <input type="password" name="user.password"/><br>
    			<input type="submit" value="登陆"/><input type="reset" value="重置"/>
  
    </form>
  </body>
</html>
