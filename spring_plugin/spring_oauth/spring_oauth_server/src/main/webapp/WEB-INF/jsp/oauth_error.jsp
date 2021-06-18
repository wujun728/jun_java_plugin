<%--
 * 
 * @author Shengzhao Li
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>Oauth Error</title>
</head>
<body>
<a href="${contextPath}/">Home</a>

<h3>
    Illegal action.
</h3>

<div class="alert alert-danger">
    <c:out value="${error.summary}"/>
</div>

</body>
</html>