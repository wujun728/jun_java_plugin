<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<!DOCTYPE html>
<html>
<head>
<title>springmvc<sitemesh:title /></title>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="${ctx}/static/bootstrap/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/static/bootstrap/css/bootstrap-theme.min.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/static/css/default.css" type="text/css" rel="stylesheet" />

<!--[if lt IE 9]>
<script src="${ctx}/static/bootstrap/js/html5shiv.js"></script>
<script src="${ctx}/static/bootstrap/js/respond.min.js"></script>
<![endif]-->
<body class="preview" id="top" data-spy="scroll" data-target=".subnav" data-offset="80">
	<div class="container" id="mainContent">
		<div class="row">
			<div class="col-sm-offset-3 col-sm-3">
				<span class="error-msg">
					<h3>
						<s:message code="evaluate.size.limit.error" />
					</h3>
				</span>
			</div>
			<div class="col-sm-1">
				<span class="error-msg">
					<h3>
						<button type="button" class="btn btn-primary btn-xs active" onclick="javascript:history.go(-1)">返回</button>
					</h3>
				</span>
			</div>
		</div>
	</div>
</body>
</html>
