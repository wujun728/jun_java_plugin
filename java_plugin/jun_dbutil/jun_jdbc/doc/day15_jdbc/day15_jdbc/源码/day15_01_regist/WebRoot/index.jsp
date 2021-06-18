<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>title</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <h1>XXX网站</h1>
    <hr/>
    <c:if test="${sessionScope.user==null}">
    	<a href="${pageContext.request.contextPath}/regist.jsp">注册</a>
    	<a href="${pageContext.request.contextPath}/login.jsp">登录</a>
    </c:if>
    <c:if test="${sessionScope.user!=null}">
    	欢迎您：${sessionScope.user.username}
    	<a href="${pageContext.request.contextPath}/servlet/LogoutServlet">注销</a>
    </c:if>
  </body>
</html>
