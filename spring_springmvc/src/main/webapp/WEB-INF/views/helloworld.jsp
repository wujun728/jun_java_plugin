<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
成功！！
nick:<%=request.getParameter("nick") %>
name:${string}
user:${user}

<hr>
<h1>conversionTest/bool</h1>
<form action="/bool" method="post">
	<input type="text" name="bool">
	<input type="submit" value="提交">
</form>
<hr>
<h1>conversionTest/Date</h1>
<form action="/date" method="post">
	<input type="text" name="date">
	<input type="submit" value="提交">
</form>
<hr>
<h1>conversionTest/User</h1>
<form action="/user" method="post">
	<input type="text" name="user">
	<input type="submit" value="提交">
</form>
<hr>
<h1>conversionTest/User2</h1>
<form action="/user2" method="post">
	<input type="text" name="user">
	<input type="submit" value="提交">
</form>
</body>
</html>