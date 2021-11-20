<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>请假</title>

<link href="<c:url value='/static/jquery/themes/base/jquery-ui.min.css?${version_css}'/>" rel="stylesheet" type="text/css" />
<script src="<c:url value='/static/jquery/ui/jquery-ui.custom.min.js?${version_js}'/>" type="text/javascript"></script>
<script src="<c:url value='/static/jquery/ui/jquery.ui.datepicker.min.js?${version_js}'/>" type="text/javascript"></script>
<script src="<c:url value='/static/jquery/ui/i18n/jquery.ui.datepicker-zh-CN.min.js?${version_js}'/>" type="text/javascript"></script>
</head>
<body>
	<form action="${ctx }/activiti/save" id="leaveBill" method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${bill.id }" /> <input type="hidden" name="userId" value="${bill.userId }" />
		<div class="form-group">
			<label for="title" class="col-sm-2 control-label">请假天数：</label>
			<div class="col-sm-3">
				<input type="text" class="form-control" name="days" id="days" value="${bill.days }" />
			</div>
		</div>
		<div class="form-group">
			<label for="content" class="col-sm-2 control-label">请假原因：</label>
			<div class="col-sm-3">
				<textarea class="form-control" name="content" id="content" row="5">${bill.content }</textarea>
			</div>
		</div>
		<div class="form-group">
			<label for="remark" class="col-sm-2 control-label">备注：</label>
			<div class="col-sm-3">
				<textarea class="form-control" name="remark" id="remark" row="5">${bill.remark }</textarea>
			</div>
		</div>
		<div class="form-group input-group-sm">
			<label class="col-sm-2 control-label">&nbsp;</label>
			<div class="col-sm-3">
				<button class="btn btn-default" type="submit" id="saveBtn">提交</button>
			</div>
		</div>
	</form>
</body>
</html>

