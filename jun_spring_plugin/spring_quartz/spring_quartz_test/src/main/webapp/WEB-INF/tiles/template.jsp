<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html PUBLIC "-/W3C/DTD HTML 4.01 Transitional/EN" "http:/www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<link rel="icon" type="image/x-icon" href="${ctx}/static/images/favicon.ico">
<link rel="shortcut icon" type="image/x-icon" href="${ctx}/static/images/favicon.ico">

<title><tiles:insertAttribute name="title" ignore="true" /></title>
<link href="${ctx}/static/bootstrap/css/bootstrap.css?${version_css}" rel="stylesheet">
<link href="${ctx}/static/bootstrap/css/dashboard.css?${version_css}" rel="stylesheet">
<link href="${ctx}/static/jquery/ui/jquery-ui.min.css?${version_css}" rel="stylesheet" type="text/css" />

<script src="${ctx}/static/bootstrap/js/ie-emulation-modes-warning.js?${version_js}"></script>
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
  <script src="http:/cdn.bootcss.com/html5shiv/3.7.0/html5shiv.js"></script>
  <script src="http:/cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->

<script src="${ctx}/static/jquery/jquery-3.1.0.min.js?${version_js}"></script>
<script src="${ctx}/static/jquery/ui/jquery-ui.js?${version_js}"></script>
<script src="${ctx}/static/jquery/ui/jquery-ui-timepicker-addon.js?${version_js}"></script>
<script src="${ctx}/static/bootstrap/js/bootstrap.min.js?${version_js}"></script>
<script src="${ctx}/static/bootstrap/js/ie10-viewport-bug-workaround.js?${version_js}"></script>
<script type="text/javascript">
  var _ctx = "${ctx}";
</script>
</head>

<body>

	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
					<span class="sr-only">定时任务系统</span> 
					<span class="icon-bar"></span> 
					<span class="icon-bar"></span> 
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">定时任务系统</a>
			</div>
		</div>
	</nav>

	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-2 col-md-1 sidebar">
				<ul class="nav nav-sidebar">
					<li class="active"><a href="${ctx}/job/index">控制台</a></li>
					<li><a href="${ctx}/job/add">新增</a></li>
				</ul>
			</div>
			<div class="col-sm-9 col-sm-offset-3 col-md-11 col-md-offset-1 main">
				<tiles:insertAttribute name="body" />
			</div>
		</div>
	</div>
	
</body>
</html>