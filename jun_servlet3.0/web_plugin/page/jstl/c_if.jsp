<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <body>
  	<c:if var="condition" test="${10>5}" scope="request">
  		10大于5
  	</c:if>	
  	<jsp:forward page="value.jsp"/>
  </body>
</html>
