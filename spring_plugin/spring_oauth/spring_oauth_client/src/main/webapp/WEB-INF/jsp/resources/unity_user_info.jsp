<%--
 * 
 * @author Shengzhao Li
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>User Info [Unity]</title>
</head>
<body>
<a href="${contextPath}/">Home</a>

<h2>User Info [Unity]
    <small>数据来源于 'spring-oauth-server' 中提供的API接口</small>
</h2>

<dl class="dl-horizontal">
    <dt>username</dt>
    <dd><code>${userDto.username}</code></dd>
    <dt>uuid</dt>
    <dd><code>${userDto.uuid}</code></dd>
    <dt>phone</dt>
    <dd><code>${userDto.phone}</code></dd>
    <dt>email</dt>
    <dd><code>${userDto.email}</code></dd>
    <dt>privileges</dt>
    <dd><code>${userDto.privileges}</code></dd>

</dl>

<a href="javascript:history.back();" class="btn btn-default">Back</a>
</body>
</html>