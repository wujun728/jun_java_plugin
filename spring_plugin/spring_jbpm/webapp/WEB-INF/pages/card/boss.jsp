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
 <ol class="breadcrumb" id="nav_activities"></li>
		</ol>
		<p>身份验证第二步</p>
		<p>身份证照片</p>
		
	<button data-url="<%=request.getContextPath()%>/leave/end/${taskId}" type="submit" class="btn btn-success" id="end">认证完成</button>
		    
</div>
<script src="//cdn.bootcss.com/jquery/2.1.4/jquery.min.js"></script>
<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/activity.js"></script>
</body>
</html>