<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>请假管理界面</title>
<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css" />
</head>
<body>
<div class="container">
    <ul class="nav nav-tabs" Style="margin-bottom:10px;">
	  <li role="presentation"><a href="<%=request.getContextPath()%>/leave/index">流程管理面板</a></li>
	  <li role="presentation" class="active"><a href="<%=request.getContextPath()%>/leave/deploy">发布</a></li>
	  <li role="presentation"><a href="#">Messages</a></li>
	</ul>
	<div class="panel panel-primary">
		<div class="panel-heading">流程列表</div>
	  <div class="panel-body">
	
	</div>
	</div>
</div>
<script src="//cdn.bootcss.com/jquery/2.1.4/jquery.min.js"></script>
<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</body>
</html>