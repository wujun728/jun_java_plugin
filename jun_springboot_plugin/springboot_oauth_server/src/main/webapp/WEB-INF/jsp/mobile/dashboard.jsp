<%--
 * 
 * @author Shengzhao Li
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>Mobile 资源</title>
</head>
<body>
<a href="${pageContext.request.contextPath}/">Home</a>

<h2>Hi Mobile
    <small>你已成功访问 [mobile] 资源</small>
</h2>

用户信息:
<br/>
<strong>${SPRING_SECURITY_CONTEXT.authentication.principal}</strong>
<br/>
<br/>
<p class="text-info">
    访问API
</p>
<a href="${pageContext.request.contextPath}/m/user_info?access_token=${param.access_token}">用户信息(JSON)</a>

</body>
</html>