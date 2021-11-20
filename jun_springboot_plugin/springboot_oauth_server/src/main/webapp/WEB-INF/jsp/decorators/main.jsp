<%--
 * 
 * @author Shengzhao Li
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8"/>
    <c:set var="contextPath" value="${pageContext.request.contextPath}" scope="application"/>

    <meta name="viewport" content="width=device-width,user-scalable=no"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="${_csrf.headerName}" content="${_csrf.token}"/>
    <link rel="shortcut icon" href="${contextPath}/static/favicon.ico"/>

    <title><sitemesh:write property='title'/> - Spring Security&OAuth2</title>

    <link href="${contextPath}/static/bootstrap.min.css" rel="stylesheet"/>
    <%--<decorator:head/>--%>
    <sitemesh:write property='head'/>

</head>
<body class="container">
<div>
    <div>
        <%--<decorator:body/>--%>
        <sitemesh:write property='body'/>
    </div>
    <div>
        <hr/>
        <p class="text-center text-muted">
            &copy; 2013 - 2019 <a href="https://gitee.com/shengzhao/spring-oauth-server" target="_blank">spring-oauth-server</a>.
            V${mainVersion}
        </p>
    </div>
</div>
</body>
</html>