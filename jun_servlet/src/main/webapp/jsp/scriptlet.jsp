<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%!
	String str="全局变量";
%>
<%!
	public void fun1(){
		System.out.println("全局方法");
	}
%>
<%!
	class C{
		private int a;
		public void f(){
			System.out.println("全局类");
		}
	}
%>
<%
	int a=1234;
	String b="java";
	out.println(a+b+"局部变量");
%>
<title>Insert title here</title>
</head>
<body>
<%=b %>
</body>
</html>