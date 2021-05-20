<%@ page language="java" contentType="text/html;charset=UTF-8"  pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<table border="1" cellpadding="0" cellspacing="0" width="60%">
    <tr>
        <td>编号</td>
        <td>头像</td>
        <td>名称</td>
        <td>年龄</td>
        <td>操作</td>
    </tr>
    <c:forEach items="${users}" var="u">
        <tr>
            <td>${u.id}</td>
            <td>${u.name}</td>
            <td>${u.age}</td>
            <td>
                <a href="/user/input?id=${u.id}">编辑</a>
                <a href="/user/delete?id=${u.id}">删除</a>
            </td>
            <td><fmt:formatDate value="${u.bornDate}" pattern="yyyy-MM-dd"/></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
