<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <body>
  	
  	<c:catch var="myException">
	  	<%
			Integer.parseInt("abc");	  	
	  	%>
    </c:catch>
    
    错误原因：<c:out value="${myException.message}"/>
   
  </body>
</html>
