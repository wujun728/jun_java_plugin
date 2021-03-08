<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
 这个文件在hello里，输出world！
 ${requestScope.time}<br/>
name= ${user.getName()}<br/>
 a=${a}
 <hr>
 <f:form id="user" modelAttribute="user">
 	<f:label path="name" htmlEscape="true"></f:label>
 	<f:select path="name">
 		<f:options items="${books}" itemLabel="street" itemValue="tel"></f:options>
 	</f:select>
 	<f:checkboxes items="${books}" path="books" itemLabel="street" itemValue="tel" htmlEscape="true" element="label"></f:checkboxes>
 </f:form>
</body>
</html>