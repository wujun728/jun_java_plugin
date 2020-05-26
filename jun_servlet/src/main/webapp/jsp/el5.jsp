<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	request.setAttribute("num1",10);
	request.setAttribute("num2",3);
	request.setAttribute("flag1",true);
	request.setAttribute("flag2",false);
%>
<h1>算数运算符</h1>
<h2>num1=${num1 },num2=${num2 }</h2>
<h2>num1+num2=${num1+num2 }</h2>
<h2>num1-num2=${num1-num2 }</h2>
<h2>num1*num2=${num1*num2 }</h2>
<h2>num1/num2=${num1/num2 }</h2>
<h2>num1%num2=${num1%num2 }</h2>
<h2>num1*(num1-num2)=${num1*(num1-num2) }</h2>
<h1>关系运算符</h1>
<h2>flag1=${flag1 },flag2=${flag2 }</h2>
<h2>与操作flag1 && flage2：${flag1 && flage2 }</h2>
<h2>或操作flag1 || flage2：${flag1 || flage2 }</h2>
<h2>非操作!flag1：${!flag1}</h2>
<h1>三目运算符</h1>
<h2>三目操作：num1>num2：${num1>num2?"yes":"no" }</h2>
<h1>empty关键字</h1>
<h2>判断空操作：${empty num1 }</h2>
</body>
</html>