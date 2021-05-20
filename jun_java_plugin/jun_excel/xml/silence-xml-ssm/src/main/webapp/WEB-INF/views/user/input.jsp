<%@ page language="java" contentType="text/html;charset=UTF-8"  pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/user/saveOrUpdate" method="POST" enctype="multipart/form-data">
    <input type="hidden" name="id" value="${user.id}">
    名称:<input type="text" name="name" value="${user.name}"><br>
    年龄:<input type="text" name="age" value="${user.age}"><br>
    生日:<input type="text" name="bornDate" value="${user.bornDate}"><br>
    头像:<input type="text" name="headImg"><br>
    <input type="submit" value="保存"/>
</form>
</body>
</html>