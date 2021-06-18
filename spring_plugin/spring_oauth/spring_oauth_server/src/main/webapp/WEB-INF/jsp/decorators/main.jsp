<%--
 * 
 * @author Shengzhao Li
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8"/>
    <c:set var="contextPath" value="${pageContext.request.contextPath}" scope="application"/>

    <meta name="viewport" content="width=device-width,user-scalable=no"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <link rel="shortcut icon" href="${contextPath}/resources/favicon.ico"/>

    <title><decorator:title default=""/> - Spring Security&OAuth2</title>

    <link href="${contextPath}/resources/bootstrap.min.css" rel="stylesheet"/>
    <style>
        body {
            font-family: "Microsoft YaHei", Arial;
        }
    </style>

    <decorator:head/>

</head>
<body class="container">
<div>
    <div>
        <decorator:body/>
    </div>
    <div>
        <hr/>
        <p class="text-center text-muted">
            &copy; 2013 - 2017
            <a href="mailto:sz@monkeyk.com">sz@monkeyk.com</a> from <a
                href="https://gitee.com/shengzhao/spring-oauth-server" target="_blank">spring-oauth-server</a>
        </p>
    </div>
</div>
</body>
</html>