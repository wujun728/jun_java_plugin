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
 <ol class="breadcrumb" id="nav_activities"></ol>
		<form class="form-horizontal" id="photoForm">
			<input type="hidden" name="taskId" value="${taskId}">
		  
		  <div class="form-group">
		    <label for="dayInput" class="col-sm-2 control-label">手机号验证</label>
		    <div class="col-sm-10">
		      <input type="text" name="phoneNo" />
		    </div>
		  </div>
		  <div class="form-group">
		    <div class="col-sm-offset-2 col-sm-10">
		      <button type="button" class="btn btn-success" id="photoButton">下一步</button>
		    </div>
		  </div>
		</form>
<script src="//cdn.bootcss.com/jquery/2.1.4/jquery.min.js"></script>
<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/activity.js"></script>
</body>
</html>