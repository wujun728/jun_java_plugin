<%--
 * 
 * @author Shengzhao Li
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>Mobile dashboard</title>
</head>
<body>
<a href="../">Home</a>

<h2>Hi Mobile.</h2>

Principal: <strong>${SPRING_SECURITY_CONTEXT.authentication.principal}</strong>
<br/>
<a href="${contextPath}/logout.do">Logout</a>

</body>
</html>