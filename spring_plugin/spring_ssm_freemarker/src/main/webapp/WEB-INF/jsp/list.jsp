<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	<table border="1px">
		<c:forEach items="${list}" var="u">
			<tr>
				<td><a href="/user/get/${u.id }">${u.userName}</a></td>
				<td>${u.age }</td>
				<td><a href="/user/del/${u.id }">删除</a></td>				
			</tr>
		</c:forEach>
	</table>
	
</body>
</html>