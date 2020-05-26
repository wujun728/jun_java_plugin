<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<!-- value:数值 ;  type:数值类型;  pattern:格式 -->
<fmt:formatNumber value="12" type="currency" pattern="＄.00"/> 
<fmt:formatNumber value="12" type="currency" pattern="＄.0#"/>
<fmt:formatNumber value="1234567890" type="currency"/> 
<fmt:formatNumber value="123456.7891" pattern="#,#00.0#"/>
</body>
</html>