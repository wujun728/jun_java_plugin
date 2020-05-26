<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>显示KaptchaServlet生成的验证码</title>

<script type="text/javascript">
         function refresh() {
                  document.getElementById('captcha_img').src="kaptcha.jpg?"+Math.random();
}

</script>


</head>

<body>
	验证码：<input type="text" name="verifyCodeActual">
	<div class="item-input">
		<img id="captcha_img" alt="点击更换" title="点击更换"
			onclick="refresh()" src="kaptcha.jpg" />
	</div>

</body>
</html>
