<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>后台管理系统登录</title>
    <meta name="keyword" content="">
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <meta name="Author" content="zifan">
    <meta name="copyright" content="胡桃夹子。All Rights Reserved">
    <link href="${ctx}/static/css/bootstrap.min.css" rel="stylesheet">
<style type="text/css">
body {
	padding-top: 13%;
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
		<form action="${ctx }/login" class="form-signin" id="userForm" method="POST">
			<h3 class="form-signin-heading">请登录</h3>
			<input type="text" class="form-control input-block-level" id="username" name="username" placeholder="用户" value="wangxin">
			<input type="password" class="form-control input-block-level" id="password" name="password" placeholder="密码" value="123456">
			<button class="btn btn-large btn-primary" type="submit">登录</button>
			</br>
			</br>
		</form>
	</div>
</body>
</html>
