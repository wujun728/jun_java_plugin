<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>示例-<sitemesh:write property='title' /></title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link rel="icon" type="image/x-icon" href="${ctx}/static/images/favicon.ico">
<link rel="shortcut icon" type="image/x-icon" href="${ctx}/static/images/favicon.ico">

<c:set var="version_css" value="20131213" scope="application"></c:set>
<c:set var="version_js" value="20131213" scope="application"></c:set>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<link href="${ctx}/static/bootstrap/css/bootstrap.min.css?${version_css}" type="text/css" rel="stylesheet" />
<link href="${ctx}/static/bootstrap/css/bootstrap-theme.min.css?${version_css}" type="text/css" rel="stylesheet" />
<link href="${ctx}/static/jquery/plugins/css/jquery.scrollToTop.css?${version_css}" type="text/css" rel="stylesheet" />
<link href="${ctx}/static/css/default.css?${version_css}" type="text/css" rel="stylesheet" />

<script src="${ctx}/static/jquery/jquery-1.9.1.js?${version_js}" type="text/javascript"></script>
<script src="${ctx}/static/bootstrap/js/bootstrap.min.js?${version_js}" type="text/javascript"></script>
<script src="${ctx}/static/js/common.js?${version_js}" type="text/javascript"></script>
<script src="${ctx}/static/jquery/plugins/js/jquery.scrollToTop.js?${version_js}" type="text/javascript"></script>
<script type="text/javascript">
  var _ctx = "${pageContext.request.contextPath}";
  jQuery(function() {
    jQuery(window).scrollToTop();
  });
</script>
<!--[if lt IE 9]>
<script src="${ctx}/static/bootstrap/js/html5shiv.js"></script>
<script src="${ctx}/static/bootstrap/js/respond.min.js"></script>
<![endif]-->

<sitemesh:write property='head' />
</head>
<body>
	<div id="wrap">
		<%@ include file="/WEB-INF/layouts/header.jsp"%>
		<div class="container">
			<div id="content">
				<sitemesh:write property='body' />
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/layouts/footer.jsp"%>
</body>
</html>