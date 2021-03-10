<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>创建条目</title>

<link href="<c:url value='/static/jquery/themes/base/jquery-ui.min.css?${version_css}'/>" rel="stylesheet" type="text/css" />

<script src="<c:url value='/static/jquery/ui/jquery-ui.custom.min.js?${version_js}'/>" type="text/javascript"></script>
<script src="<c:url value='/static/jquery/ui/jquery.ui.datepicker.min.js?${version_js}'/>" type="text/javascript"></script>
<script src="<c:url value='/static/jquery/ui/i18n/jquery.ui.datepicker-zh-CN.min.js?${version_js}'/>" type="text/javascript"></script>
<script src="${ctx}/static/js/news/news.js?${version_js}" type="text/javascript"></script>
</head>
<body>
	<form:form action="${ctx }/news" commandName="mewsCommand" id="newsForm" method="post" class="form-horizontal">
		<div class="form-group">
			<label for="title" class="col-sm-2 control-label">标题：</label>
			<div class="col-sm-4">
				<input type="text" class="form-control" name="title" id="title" />
			</div>
			<form:errors cssClass="label label-danger" path="title"></form:errors>
		</div>
		<div class="form-group">
			<label for="description" class="col-sm-2 control-label">内容：</label>
			<div class="col-sm-4">
				<textarea class="form-control" name="description" id="description" row="15"></textarea>
			</div>
			<form:errors cssClass="label label-danger" path="description"></form:errors>
		</div>
		<div class="form-group">
			<label for="address" class="col-sm-2 control-label">地址：</label>
			<div class="col-sm-4">
				<input type="text" class="form-control" name="address" id="address" />
			</div>
			<form:errors cssClass="label label-danger" path="address"></form:errors>
		</div>

		<div class="form-group">
			<label for="newsTime" class="col-sm-2 control-label">发生时间：</label>
			<div class="col-sm-4">
				<input type="text" class="form-control" name="newsTime" id="newsTime" readonly="readonly" />
			</div>
			<form:errors cssClass="label label-danger" path="newsTime"></form:errors>
		</div>


		<div class="form-group input-group-sm">
			<label class="col-sm-2 control-label">&nbsp;</label>
			<div class="col-sm-2">
				<button class="btn btn-default" type="button" id="saveBtn">保存</button>
				<button class="btn btn-default" type="reset">取消</button>
			</div>
		</div>
	</form:form>
</body>
</html>

