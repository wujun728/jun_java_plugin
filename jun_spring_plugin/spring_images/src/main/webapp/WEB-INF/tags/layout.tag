<%@ tag description="main page layout" %>

<%@ attribute name="style" %>
<%@ attribute name="script" %>
<%@ attribute name="title" required="true" %>

<%@ attribute name="head" fragment="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Spring Images - ${title}</title>
        
        <jsp:invoke fragment="head" />
        
        <link rel="stylesheet/less" href="${pageContext.request.contextPath}/css/application.less" />
        <c:forTokens var="file" items="${style}" delims=",">
            <link rel="stylesheet/less" href="${pageContext.request.contextPath}/css/${file}.less" />
        </c:forTokens>
        <script src="${pageContext.request.contextPath}/js/less-1.1.5.min.js"></script>
        
        <script src="${pageContext.request.contextPath}/js/jquery-1.7.1.js" ></script>
        <c:forTokens var="file" items="${script}" delims=",">
            <script src="${pageContext.request.contextPath}/js/${file}.js"></script>
        </c:forTokens>
    </head>
    <body>
        <div id="topmenu">
            <ul>
                <li><a href="${pageContext.request.contextPath}/images">Images</a></li>
                <li><a href="${pageContext.request.contextPath}/images/upload">Upload</a></li>
            </ul>
        </div>
        
        <div id="content">
            <jsp:doBody />
        </div>
    </body>
</html>
