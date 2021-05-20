<%@ page language="java" pageEncoding="UTF-8"%>
<%-- <%@ page import="com.jun.jstl.*" %> --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <body>
    <%
    	session.setAttribute("user","user");
    %>
  	用户名： <c:out value="${user.username}" default="无名士"/><br/>
  	地址：<c:out value="${user.address.location}"/>
  	<hr/>
  	<c:out value="<a href='#'>下载</a>" escapeXml="false"/>
  </body>
</html>
