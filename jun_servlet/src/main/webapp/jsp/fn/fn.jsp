<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	pageContext.setAttribute("info","www.java1234.com");
%>
<h2>查找java1234位置:${fn:indexOf(info,"java1234")}</h2>
<h2>判断java1234是否存在:${fn:contains(info,"java1234")}</h2>
<h2>截取:${fn:substring(info,0,5)}</h2>
<h2>拆分:${fn:split(info,".")[1]}</h2>
</body>
</html>