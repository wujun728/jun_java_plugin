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
	  <li role="presentation" class="active"><a href="<%=request.getContextPath()%>/leave/index"><span class="glyphicon glyphicon-home" aria-hidden="true"></span> 流程管理面板</a></li>
	 <li role="presentation"><a href="<%=request.getContextPath()%>/leave/history"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span> 流程记录</a></li>
	 <li role="presentation"><a href="<%=request.getContextPath()%>/leave/setting"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span> 设置流程</a></li>
	 
	   <li role="presentation" class="dropdown">
	    <a class="dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">
	      	<span class="glyphicon glyphicon-user" aria-hidden="true"></span> 您好，${userName} <span class="caret"></span>
	    </a>
	    <ul class="dropdown-menu">
	      <li role="presentation"><a href="<%=request.getContextPath()%>/user/logout">退出</a></li>
	    </ul>
	  </li>
	   <li role="presentation"><a href="#">当前流程：${flowName}</a></li>
	</ul>
	<form class="form-horizontal" id="indexForm">
  <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">用户名</label>
    <div class="col-sm-10">
      <input type="text" class="form-control" id="inputEmail3" placeholder="Email" value="kenfo">
    </div>
  </div>
  <div class="form-group">
    <label for="inputPassword3" class="col-sm-2 control-label">邮箱</label>
    <div class="col-sm-10">
      <input type="email" class="form-control" id="inputPassword3" placeholder="Password"  value="xxg3053@qq.com">
    </div>
  </div>
  <div class="form-group">
    <label for="inputPassword3" class="col-sm-2 control-label">地址</label>
    <div class="col-sm-10">
      <input type="text" class="form-control" id="inputPassword3" placeholder="Password" value="深南大道">
    </div>
  </div>
  <div class="form-group">
    <div class="col-sm-offset-2 col-sm-10">
      <a class="btn btn-primary" data-url="<%=request.getContextPath()%>/leave/start?userName=${userName}&flowName=${flowName}" href="#" id="start">修改</a>
	
    </div>
  </div>
</form>
						

	
</div>

<!-- 处理流程 -->
<div class="modal fade" id="dealModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">修改资料</h4>
      </div>
      <div class="modal-body">
      <!--  -->
      <iframe name="dealIframe" frameborder="0" height="200" width="100%" src="">
      
      </iframe>
      </div>
    </div>
  </div>
</div>
<script src="//cdn.bootcss.com/jquery/2.1.4/jquery.min.js"></script>
<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/main.js"></script>
</body>
</html>