<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<title>欢迎登录</title>

<link href="${ctx}/static/bootstrap/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<style type="text/css">
body {
	padding-top: 40px;
	padding-bottom: 40px;
	background-color: #f5f5f5;
}

.form-signin {
	max-width: 300px;
	padding: 19px 29px 29px;
	margin: 0 auto 20px;
	background-color: #fff;
	border: 1px solid #e5e5e5;
	-webkit-border-radius: 5px;
	-moz-border-radius: 5px;
	border-radius: 5px;
	-webkit-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
	-moz-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
	box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
}

.form-signin input[type="text"], .form-signin input[type="password"] {
	font-size: 16px;
	height: auto;
	margin-bottom: 15px;
	padding: 7px 9px;
}

.login-error {
	color: #C7254E;
	font-size: 90%;
	display: block;
	white-space: nowrap;
}
</style>
<!--[if lt IE 9]>
    <script src="${ctx}/static/bootstrap/js/html5shiv.js"></script>
    <script src="${ctx}/static/bootstrap/js/respond.min.js"></script>
    <![endif]-->
</head>

<body>

	<div class="container">
		<form:form action="${ctx }/login" cssClass="form-signin" modelAttribute="userCommand" method="POST">
			<h3 class="form-signin-heading">请登录</h3>
			<input type="text" class="form-control input-block-level" id="username" name="username" placeholder="用户" value="fbb">
			<input type="password" class="form-control input-block-level" id="password" name="password" placeholder="密码" value="123456">
			<form:errors cssClass="login-error"></form:errors>
			<button class="btn btn-large btn-primary" type="submit">登录</button>
			</br>
			</br>
		</form:form>
	</div>
	<br />
	<div class="container">
		<div class="row col-md-offset-5">
			<strong>王中军</strong>&nbsp;：&nbsp;wzj&nbsp;/&nbsp;123456
		</div>
		<div class="row col-md-offset-5">
			<strong>冯小刚经纪人</strong>&nbsp;：&nbsp;fxg_manager&nbsp;/&nbsp;123456
		</div>
		<div class="row col-md-offset-5">
			<strong>范冰冰经纪人</strong>&nbsp;：&nbsp;fbb_manager&nbsp;/&nbsp;123456
		</div>
		<div class="row col-md-offset-5">
			<strong>冯小刚</strong>&nbsp;：&nbsp;fxg&nbsp;/&nbsp;123456
		</div>
		<div class="row col-md-offset-5">
			<strong>范冰冰</strong>&nbsp;：&nbsp;fbb&nbsp;/&nbsp;123456
		</div>
	</div>

</body>
</html>
