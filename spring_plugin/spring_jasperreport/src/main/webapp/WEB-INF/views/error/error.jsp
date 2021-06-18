<%--
  Created by IntelliJ IDEA.
  User: liuburu
  Date: 2017/10/31
  Time: 23:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>错误页面</title>
</head>
<body>
<h2>错误码:${statusCode}</h2>
<c:if test="${statusCode eq 404}">
    <h1>404</h1>
    <br>
    <p>对不起，暂时没有找到你访问的页面。</p>
</c:if>
</body>
</html>
