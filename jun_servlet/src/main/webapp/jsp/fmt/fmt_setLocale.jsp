<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	pageContext.setAttribute("date",new Date());
%>
中文日期：
<fmt:setLocale value="zh_CN"/>
<fmt:formatDate value="${date }"/>
<hr/>
英文日期：
<fmt:setLocale value="en_US"/>
<fmt:formatDate value="${date }"/>
</body>
</html>