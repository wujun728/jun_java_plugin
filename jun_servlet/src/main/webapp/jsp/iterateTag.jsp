<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ taglib prefix="java1234" uri="/WEB-INF/java1234.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%
	List people=new ArrayList();
	people.add("王二小");
	people.add("丝丝光");
	people.add("草泥马");
	pageContext.setAttribute("people", people);
%>
</head>
<body>
<java1234:iterate items="people" var="p">
	<h2>${p }</h2>
</java1234:iterate>
</body>
</html>