<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
 * 
 * @author Shengzhao Li
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>User Overview</title>
</head>
<body>
<a href="../">Home</a>

<h2>User Overview</h2>

<div class="pull-right">
    <a href="form/plus" class="btn btn-success btn-sm">Add User</a>
</div>
<form action="" class="form-inline">
    <div class="form-group">
        <input type="text" class="form-control" placeholder="Type username" name="username"
               value="${overviewDto.username}"/>
    </div>
    <button type="submit" class="btn btn-default">Search</button>
    &nbsp;<span class="text-info">Total: ${overviewDto.size}</span>
</form>
<br/>
<table class="table table-bordered table-hover table-striped">
    <thead>
    <tr>
        <th>Username</th>
        <th>Privilege</th>
        <th>Phone</th>
        <th>Email</th>
        <th>CreateTime</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${overviewDto.userDtos}" var="user">
        <tr>
            <td>${user.username}</td>
            <td>${user.privileges}</td>
            <td>${user.phone}</td>
            <td>${user.email}</td>
            <td>${user.createTime}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>