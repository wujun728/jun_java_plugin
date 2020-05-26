<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<fmt:setLocale value="zh_CN"/>
<fmt:bundle basename="info">
	<fmt:message key="name" var="userName"/>
</fmt:bundle>
<h2>姓名：${userName }</h2>
<fmt:bundle basename="info">
	<fmt:message key="info" var="infomation">
		<fmt:param value="<font color='red'>小锋</font>"/>
	</fmt:message>
</fmt:bundle>
<h2>信息：${infomation }</h2>
<hr/>
<fmt:setLocale value="en_US"/>
<fmt:bundle basename="info">
	<fmt:message key="name" var="userName"/>
</fmt:bundle>
<h2>姓名：${userName }</h2>
<fmt:bundle basename="info">
	<fmt:message key="info" var="infomation">
		<fmt:param value="<font color='red'>小锋</font>"/>
	</fmt:message>
</fmt:bundle>
<h2>信息：${infomation }</h2>
</body>
</html>