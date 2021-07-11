<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>用户登录</title>
<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css" />
</head>
<body>
<div class="container">
<h1>测试系统登录</h1>
	<c:if test="${not empty message }">
   		 <div class="alert alert-warning" role="alert"> ${message}</div>
   </c:if>
 <div class="row">
 	<div class="col-sm-4">
 			<div class="panel panel-info">
				<div class="panel-heading"><h4>修改资料三部认证系统  <small>用户登录</small></h4></div> 
			  <div class="panel-body">
			  	 <ul class="nav nav-tabs" role="tablist">
				    <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">普通用户</a></li>
				  </ul>
				
				  <!-- Tab panes -->
				  <div class="tab-content">
				    <div role="tabpanel" class="tab-pane active" id="home">
				    	<p></p>
				    		<form class="form-horizontal" action="<%=request.getContextPath()%>/user/doLogin" method="POST">
				    			<input type="hidden" value="leave" name="type"/>
								<div class="form-group">
								    <label for="inputName" class="col-sm-3 control-label">用户名:</label>
								    <div class="col-sm-9">
								      <input type="text" class="form-control" id="inputName" placeholder="kenfo" name="userName">
								    </div>
								  </div>
								  <div class="form-group">
								    <label for="inputEmail" class="col-sm-3 control-label">邮箱:</label>
								    <div class="col-sm-9">
								      <input type="text" class="form-control" id="inputEmail" placeholder="xxg3053@qq.com" name="email">
								    </div>
								  </div>
								  
								  <div class="form-group">
								    <div class="col-sm-offset-2 col-sm-10">
								      <button type="submit" class="btn btn-primary">登录</button>
								    </div>
								  </div>
								
							</form>
				    </div>
				  </div>
				
				</div>
			</div>
 	
 	</div>
 	
 	
 </div>
  <div class="row">
 	
 </div>
</div>
<script src="//cdn.bootcss.com/jquery/2.1.4/jquery.min.js"></script>
<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</body>
</html>