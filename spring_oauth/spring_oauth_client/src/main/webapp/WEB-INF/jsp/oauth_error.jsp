<%--
 * 
 * @author Shengzhao Li
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>Oauth Error</title>
</head>
<body>
<a href="${contextPath}/">Home</a>

<h3>Oauth Error</h3>

<div class="alert alert-danger">
    <h4>${error}</h4>
    ${message}
</div>
</body>
</html>