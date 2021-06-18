<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>新闻列表</title>

<link href="<c:url value='/static/jquery/themes/base/jquery-ui.min.css?${version_css}'/>" rel="stylesheet" type="text/css" />

<script src="<c:url value='/static/jquery/ui/jquery-ui.custom.min.js?${version_js}'/>" type="text/javascript"></script>
<script src="<c:url value='/static/jquery/ui/jquery.ui.datepicker.min.js?${version_js}'/>" type="text/javascript"></script>
<script src="<c:url value='/static/jquery/ui/i18n/jquery.ui.datepicker-zh-CN.min.js?${version_js}'/>" type="text/javascript"></script>
<script src="${ctx}/static/js/news/news_list.js?${version_js}" type="text/javascript"></script>
</head>
<body>

	<form:form class="form-horizontal">
		<div class="form-group">
			<div class="col-sm-4">
				<input type="text" class="form-control" name="keywords" id="keywords" />
			</div>
			<div class="col-sm-2">
				<button class="btn btn-default" type="button" id="searchBtn">搜索</button>
			</div>
		</div>
	</form:form>
	<div class="row">
		<table class="table table-condensed">
			<thead>
				<tr>
					<th>标题</th>
					<th>内容</th>
					<th>发生时间</th>
					<th>创建时间</th>
					<th>地址</th>
				</tr>
			</thead>
			<tbody id="newsBody">

			</tbody>
		</table>
	</div>
</body>
</html>

