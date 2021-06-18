<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <body>
   <%
   		session.setAttribute("username","杰克");
   %>
   
   <c:remove var="username" scope="session"/>
   <%-- 
   --%>
   
   欢迎<c:out value="${username}" default="无名士"/>光临
   
   
  </body>
</html>
