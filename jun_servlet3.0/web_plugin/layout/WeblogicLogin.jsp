<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>Oracle WebLogic Server 管理控制台</title>
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/css/general.css">
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/css/window.css">
<link rel="stylesheet" type="text/css" 	href="${pageContext.request.contextPath}/css/login.css">
<script type="text/javascript">
	// Disable frame hijacking  
	if (top != self)
		top.location.href = location.href;
</script>
<style type="text/css">
html {
	background-color: #185E87;
}
</style>
</head>
<body onload="document.loginData.j_username.focus();">
	<div id="top">
		<div id="login-header">
			<div id="logo">
				<img src="${pageContext.request.contextPath}/images/Branding_Login_WeblogicConsole.gif" alt="Oracle WebLogic Server 管理控制台 ">
			</div>
		</div>
		<div id="content">
			<div id="sidebar">
				<img src="${pageContext.request.contextPath}/images/Login_11gLogo1.gif" alt="">
			</div>
			<div id="login">
				<div id="title">欢迎使用</div>
				<div id="login-form">
					<form id="loginData" name="loginData" method="post" action="/console/j_security_check">
						<div class="message-row">
							<noscript>
								<p class="loginFailed">需要 JavaScript。启用 JavaScript 以便使用 WebLogic 管理控制台。</p>
							</noscript>
							<p>登录以使用 WebLogic Server 域</p>
						</div>
						<div class="input-row">
							<label for="j_username"> 用户名:</label> <span class="ctrl">
								<input class="textinput" type="text" autocomplete="on" name="j_username" id="j_username">
							</span>
						</div>
						<div class="input-row">
							<label for="j_password"> 口令:</label> <span class="ctrl"> <input class="textinput" type="password" autocomplete="on" name="j_password" id="j_password">
							</span>
						</div>
						<div class="button-row">
							<span class="ctrl"> <input class="formButton" type="submit" 
							onclick="form.submit();this.disabled=true;document.body.style.cursor = 'wait'; this.className='formButton-disabled';" value='登录'>
							</span> <input type="hidden" name="j_character_encoding" value="UTF-8">
						</div>
					</form>
				</div>
			</div>
		</div>
		<div id="info"></div>
	</div>
	<div class="login-footer">
		<div class="info">
			<p id="footerVersion">WebLogic Server 版本: 10.3.4.0</p>
			<p id="copyright">版权所有 (c) 1996, 2010, Oracle 和/或其子公司。保留所有权利。</p>
			<p id="trademark">Oracle 是 Oracle Corporation 和/或其子公司的注册商标。其它名称可能是各自所有者的商标。</p>
		</div>
	</div>
</body>
</html>
