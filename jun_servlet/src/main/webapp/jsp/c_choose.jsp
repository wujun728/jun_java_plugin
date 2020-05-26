<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<jsp:useBean id="people" class="com.java1234.model.People" scope="page"></jsp:useBean>
<c:set property="id" target="${people }" value="007"></c:set>
<c:set property="name" target="${people }" value="王二小"></c:set>
<c:set property="age" target="${people }" value="19"></c:set>


<c:choose>
	<c:when test="${people.age<18 }">
		<h2>小于18</h2>
	</c:when>
	<c:when test="${people.age==18 }">
		<h2>等于18</h2>
	</c:when>
	<c:otherwise>
		<h2>大于18</h2>
	</c:otherwise>
</c:choose>
</body>
</html>