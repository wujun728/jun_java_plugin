<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>修改资料三步认证管理界面</title>
<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css" />


</head>
<body>
<div class="container">
    <ul class="nav nav-tabs" Style="margin-bottom:10px;margin-top:10px;">
	  <li role="presentation"><a href="<%=request.getContextPath()%>/leave/index"><span class="glyphicon glyphicon-home" aria-hidden="true"></span> 流程管理面板</a></li>
	 <li role="presentation"><a href="<%=request.getContextPath()%>/leave/history"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span> 流程记录</a></li>
	 <li role="presentation"  class="active"><a href="<%=request.getContextPath()%>/leave/setting"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span> 设置流程</a></li>
	 
	   <li role="presentation" class="dropdown">
	    <a class="dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">
	      	<span class="glyphicon glyphicon-user" aria-hidden="true"></span> 您好，${userName} <span class="caret"></span>
	    </a>
	    <ul class="dropdown-menu">
	      <li role="presentation"><a href="<%=request.getContextPath()%>/user/logout">退出</a></li>
	    </ul>
	  </li>
	</ul>
	
	<form class="form-inline" method="POST" action="<%=request.getContextPath()%>/leave/doSetting">
	  <div class="form-group">
	    <label for="exampleInputName2">设置流程</label>
	    <select class="form-control" name="flowName">
	     	  <option value=""></option>
			  <option value="leave">三部认证</option>
			  <option value="two">二部认证</option>
			</select>
	  </div>
	  <button type="submit" class="btn btn-primary">设置</button>
	</form>
	
</div>

<script src="//cdn.bootcss.com/jquery/2.1.4/jquery.min.js"></script>
<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/main.js"></script>
</body>
</html>