<%--
 * 
 * @author Shengzhao Li
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>Unity 资源</title>
</head>
<body>
<a href="${pageContext.request.contextPath}/">Home</a>

<h2>Hi Unity
    <small>你已成功访问 [unity] 资源</small>
</h2>

用户信息:
<br/>
<strong>${SPRING_SECURITY_CONTEXT.authentication.principal}</strong>
<br/>
<br/>
<p class="text-info">
    访问API
</p>
<a href="${pageContext.request.contextPath}/unity/user_info?access_token=${param.access_token}">用户信息(JSON)</a>

</body>
</html>