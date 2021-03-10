<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户列表</title>
</head>
<body>
	<h3>所有用户</h3>
	
	<div>
		
			<c:forEach var="user" items="${users}">
				<div>
				${user.id }  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				${user.account }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				${user.name }
				</div>
			</c:forEach>
		
	
	</div>
	
</body>
</html>