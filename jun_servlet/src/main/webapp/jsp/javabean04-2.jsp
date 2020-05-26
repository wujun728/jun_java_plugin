<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
<jsp:useBean id="student" scope="request" class="com.java1234.model.Student"/>
<jsp:setProperty property="name" name="student" value="王八蛋"/>
<jsp:setProperty property="age" name="student" value="12"/>
<jsp:forward page="target01.jsp"/>
</body>
</html>