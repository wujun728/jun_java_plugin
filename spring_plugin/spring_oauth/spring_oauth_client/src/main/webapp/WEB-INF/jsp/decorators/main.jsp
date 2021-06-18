<%--
 * 
 * @author Shengzhao Li
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html ng-app>
<head>
    <meta charset="utf-8"/>
    <c:set var="contextPath" value="${pageContext.request.contextPath}" scope="application"/>

    <meta name="viewport" content="width=device-width,user-scalable=no"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>

    <title><decorator:title default=""/> - Spring Security&OAuth2 Client</title>

    <link href="${contextPath}/resources/bootstrap/bootstrap.min.css" rel="stylesheet"/>
    <script src="${contextPath}/resources/angular.min.js"></script>

    <decorator:head/>

</head>
<body>
<div class="container">
    <div>
        <decorator:body/>
    </div>

    <%--footer--%>
    <div class="row">
        <div class="col-md-12">
            <hr/>
            <p class="text-center text-muted">
                &copy; 2013 - 2018
                <a href="mailto:sz@monkeyk.com">sz@monkeyk.com</a> from <a
                    href="http://git.oschina.net/shengzhao/spring-oauth-server" target="_blank">spring-oauth-server</a>
            </p>
        </div>
    </div>
</div>
</body>
</html>