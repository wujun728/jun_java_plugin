<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
 	
	<#assign userlist = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j']/>
	
	
	<#if (userlist?size = 0)>
		¿ÕµÄ
		<#else>
		·Ç¿Õ
	</#if>
</body>
</html>