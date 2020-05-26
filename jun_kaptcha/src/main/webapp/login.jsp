<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>登录</title> 

<script type="text/javascript">
         function refresh() {
            document.getElementById('captcha_img').src="kaptcha.jpg?"+Math.random();
         }      
</script>

</head>

<body>
	<form action="${pageContext.request.contextPath }/user/login" method="post">
		userName:<input type="text" name="userName" /><br /> 
		password:<input type="password" name="passWord" /><br />
		验证码:  <input type="text" placeholder="请输入验证码" name="verifyCodeActual">
	            <div class="item-input">
		           <img id="captcha_img" alt="点击更换" title="点击更换"
			            onclick="refresh()" src="kaptcha.jpg" />
	               </div>
	            <input type="submit" value="登录" />
	</form>
	
</body>
</html>