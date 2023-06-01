<%--
 * 
 * @author Shengzhao Li
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset='utf-8'/>
    <meta content='IE=edge' http-equiv='X-UA-Compatible'/>
    <link href="${pageContext.request.contextPath}/static/icon2.png" rel="shortcut icon"
          type="image/vnd.microsoft.icon"/>
    <link href="${pageContext.request.contextPath}/static/bootstrap/bootstrap.min.css" media="screen" rel="stylesheet"
          type="text/css"/>

    <title>spring-dynamic-job</title>
</head>
<body>
<div class="container">
    <h3>
        spring-dynamic-job is working...
        <br/>
        ${date}
    </h3>

    <c:if test="${empty param.result}">
        <p>
            <a href="add_job.xhtm">Add Dynamic Job</a>
        </p>
    </c:if>

    <c:if test="${not empty param.result}">
        <p style="color:blue;">
            A new DynamicJob added, please see console log...
        </p>
        <a href="remove_job.xhtm">Remove Added Dynamic Job</a>
    </c:if>

</div>
</body>
</html>