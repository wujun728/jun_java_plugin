<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>
<h2>你好 maven web 项目!</h2>
<h3>now:<%= new java.util.Date() %></h3>
<h3>应用名：${pageContext.request.contextPath}</h3>
<hr/>

</body>
</html>
