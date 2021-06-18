<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="zh-CN">
<head>
<title>学习示例首页</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link href="<c:url value='/static/bootstrap/css/bootstrap.min.css'/>" type="text/css" rel="stylesheet" />

<script src="<c:url value='/static/js/jquery-1.10.2.min.js'/>" type="text/javascript"></script>
<script src="<c:url value='/static/bootstrap/js/bootstrap.min.js'/>" type="text/javascript"></script>


</head>

<body>
	<div class="navbar-wrapper">
		<div class="container">
			<form:form action="${ctx }/user/register.htm" commandName="user" method="post" >
				<div class="form-group">
					<label for="name">用户名</label> <input type="text" class="form-control" id="name" name="name" placeholder="请输入用户名">
					<form:errors path="name" cssClass="label label-danger" />
				</div>
				<div class="form-group">
					<label for="password">密码</label> <input type="password" class="form-control" id="password" name="password" placeholder="请输入密码">
					<form:errors path="password" cssClass="label label-danger" />
				</div>
				<div class="form-group">
					<label for="passwordAgain">确认密码</label> <input type="password" class="form-control" id="passwordAgain" name="passwordAgain" placeholder="请输入确认密码">
					<form:errors path="passwordAgain" cssClass="label label-danger" />
				</div>
				<button type="submit" class="btn btn-default">注册</button>
			</form:form>
		</div>
	</div>
</body>
</html>
