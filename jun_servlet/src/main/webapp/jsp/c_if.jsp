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
<c:set property="age" target="${people }" value="16"></c:set>
<c:if test="${people.name=='王二小' }" var="r" scope="page">
	<h2>是王二小</h2>
</c:if>
<c:if test="${people.age<18 }">
	<h2>是未成年</h2>
</c:if>
</body>
</html>