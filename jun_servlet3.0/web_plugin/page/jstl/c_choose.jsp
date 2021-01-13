<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <body>
  	<c:set var="age" value="${param.age}" scope="page"/>
  	<c:choose>
		<c:when test="${age<16}">
			你未成年
		</c:when>
		<c:otherwise>
			你已成年
		</c:otherwise>  	
  	</c:choose>	
  </body>
</html>
