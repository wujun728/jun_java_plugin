<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<!-- value:数值 ;  type:数值类型;  pattern:格式 -->
<%
	Date date=new Date();
	pageContext.setAttribute("date",date);
%>
<fmt:formatDate value="${date }" pattern="yyyy-MM-dd HH:mm:ss"/>
<hr/>
<fmt:formatDate value="${date }" pattern="yyyy-MM-dd"/>
</body>
</html>