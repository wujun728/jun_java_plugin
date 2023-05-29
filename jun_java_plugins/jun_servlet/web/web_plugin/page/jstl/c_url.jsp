<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.net.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <body>
    <%-- a标签是基本标签 --%>
    <a href="/day17/value.jsp?country=<%=URLEncoder.encode("中国","UTF-8")%>">
    	传统方式传中文
    </a>
  	<hr/>
  	
  	<%-- c:url不是基本标签,底层对应标签处理类，以转发方式进行 --%>
  	<c:url var="myURL" value="/value.jsp" scope="page">
  		<c:param name="country" value="中国"/>
  	</c:url>
	<a href="${myURL}">  	
  		现代方式传中文
  	</a>
  </body>
</html>
