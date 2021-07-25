<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<html>
<head>
	<title>用户登录</title>
	<style type="text/css">
		.form{
			margin: 0 auto;
			margin-top : 300px;
			width: 300px;
			height: 600px;
		}
		.btn{border: 0;background-color: #428bca;}
		
		
	</style>
</head>
<body background="${pageContext.request.contextPath }/static/image/bgroudimg.png" style="background-attachment: scroll;margin: auto;">
	
	<form  class="form" action="${pageContext.request.contextPath }/user/login" method="post">
		<span style="color: red;">${message }</span><br/>
		登录名：<input type="text" name="username" /><br>
		密码：<input type="password" name="password" /><br>
	    <input class="btn" type="submit" value="登录"/><input type="reset" value="清空"/>  
	
	</form>
</body>
</html>
