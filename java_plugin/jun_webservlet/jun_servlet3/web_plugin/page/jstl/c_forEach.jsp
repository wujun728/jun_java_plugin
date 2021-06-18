<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <body>
	<%
		List<String> stringList = new ArrayList<String>();
		stringList.add("汽车");
		stringList.add("房子");
		stringList.add("新闻");
		stringList.add("娱乐");
		application.setAttribute("STRINGLIST",stringList);
	%>  	
	
	<c:forEach var="s" items="${STRINGLIST}" varStatus="status">
		${status.index}-${status.count}-${s}-${status.first}-${status.last}<br/>
	</c:forEach>

  </body>
</html>
