<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="f" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<f:form action="validation" method="post" modelAttribute="user">
		id:<input type="text" name="id">
		<f:errors path="id"></f:errors>
		name:<input type="text" name="name">
		<f:errors path="name"></f:errors>
		money:<input type="text" name="money">
		<f:errors path="money"></f:errors>
		birthday:<input type="text" name="birthdaty">
		<f:errors path="birthday"></f:errors>
		<input type="submit" value="提交"/>
	</f:form>
</body>
</html>