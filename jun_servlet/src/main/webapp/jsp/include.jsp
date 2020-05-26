<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
<%-- <h1>静态包含</h1>
<%@ include file="common/head.html" %>
<p>content</p>
<%@ include file="common/footer.jsp" %> --%>
<h1>动态包含</h1>
<jsp:include page="common/head.html"/>
<p>content</p>
<jsp:include page="common/footer.jsp"/>
</body>
</html>