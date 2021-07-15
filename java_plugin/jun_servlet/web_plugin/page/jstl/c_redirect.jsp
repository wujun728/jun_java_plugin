<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.net.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <body>
	<c:redirect url="/value.jsp">
		<c:param name="country" value="美国"/>
		<c:param name="tel" value="XXX"/>
	</c:redirect>
  </body>
</html>
