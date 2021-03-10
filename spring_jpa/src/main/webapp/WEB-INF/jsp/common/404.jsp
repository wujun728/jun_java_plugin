<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title>404</title>
<link href="${ctx}/static/bootstrap/css/bootstrap.min.css?${version_css}" type="text/css" rel="stylesheet" />
<link href="${ctx}/static/css/default.css?${version_css}" type="text/css" rel="stylesheet" />
<style type="text/css">
.center {
	text-align: center;
	margin-left: auto;
	margin-right: auto;
	margin-bottom: auto;
	margin-top: auto;
}
</style>
</head>

<body class="preview" id="top" data-spy="scroll" data-target=".subnav" data-offset="80">
	<div class="container">
		<div class="row">
			<div class="span12">
				<div class="hero-unit center">
					<h1>
						找不到页面，<small><font face="Tahoma" color="red">404 错误</font></small>
					</h1>
					<br />
					<p>
						您请求的页面无法找到，再试一次或者，或使用浏览器的<b>返回</b>按钮，导航到您之前访问的网页。
					</p>
					<p>
						<b>或者您可以点击下面这个小按钮：</b>
					</p>
					<a href="${ctx }/index.htm" class="btn btn-large btn-info"><i class="icon-home icon-white"></i> 返回首页</a>
				</div>
				<br />

			</div>
		</div>
	</div>
</body>
</html>
