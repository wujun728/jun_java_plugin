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
	  <li role="presentation"  class="active"><a href="#">查看流程详情</a></li>
	 
	   <li role="presentation" class="dropdown">
	    <a class="dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">
	      	您好，${userName} <span class="caret"></span>
	    </a>
	    <ul class="dropdown-menu">
	      <li role="presentation"><a href="<%=request.getContextPath()%>/user/logout">退出</a></li>
	    </ul>
	  </li>
	</ul>
	<div class="panel panel-primary">
		<div class="panel-heading">流程图</div>
	  <div class="panel-body" style="position:relative;height:600px;">
		<img src="<%=request.getContextPath()%>/resources/imgs/leave.png"
		style="position: absolute; left: 0px; top: 0px" />
		<div style="position:absolute;border:2px solid red;left:${ac.x}px;top:${ac.y}px;width:${ac.width}px;height:${ac.height}px;"></div>
	</div>
	</div>
</div>
<script src="//cdn.bootcss.com/jquery/2.1.4/jquery.min.js"></script>
<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</body>
</html>