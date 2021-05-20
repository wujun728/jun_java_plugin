<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <body>
    <%-- 
  	<jsp:useBean id="user" class="cn.itcast.web.jsp.jstl.User" scope="page"/>
	<c:set target="${user}" property="username" value="叮叮"/>
	用户名：<c:out value="${user.username}"/>
	--%>
	
	<jsp:useBean id="map" class="java.util.HashMap" scope="page"/>
	<c:set target="${map}" property="key" value="1"/>
	<c:set target="${map}" property="value" value="jack"/>
	用户ID号：<c:out value="${map.key}"/><br/>
	用户姓名：<c:out value="${map.value}"/><br/>
	
  </body>
</html>
