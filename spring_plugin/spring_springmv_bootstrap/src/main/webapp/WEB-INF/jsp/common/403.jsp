<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title>403</title>
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

<body>

	<div class="container">
		<div class="row">
			<div class="span12">
				<div class="hero-unit center">
					<br /> <br /> <br /> <br /> <br />
					<p>
					<h4>对不起，您没有权限访问该页面。
				</div>
				<br />
			</div>

		</div>
	</div>

</body>
</html>
