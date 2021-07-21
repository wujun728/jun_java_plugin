<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>个人任务管理列表</title>

<link href="<c:url value='/static/jquery/themes/base/jquery-ui.min.css?${version_css}'/>" rel="stylesheet" type="text/css" />

<script src="<c:url value='/static/jquery/ui/jquery-ui.custom.min.js?${version_js}'/>" type="text/javascript"></script>
<script src="<c:url value='/static/jquery/ui/jquery.ui.datepicker.min.js?${version_js}'/>" type="text/javascript"></script>
<script src="<c:url value='/static/jquery/ui/i18n/jquery.ui.datepicker-zh-CN.min.js?${version_js}'/>" type="text/javascript"></script>

</head>
<body>


	<div class="panel panel-primary">
		<div class="panel-heading">个人任务管理列表</div>
		<table class="table table-condensed">
			<thead>
				<tr class="success">
					<th>任务ID</th>
					<th>任务名称</th>
					<th>创建时间</th>
					<th>办理人</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody id="leaveBillBody">
				<c:forEach var="dep" items="${list }" varStatus="cur">
					<tr>
						<td>${dep.id }</td>
						<td>${dep.name }</td>
						<td><fmt:formatDate value="${dep.createTime}" type="both" /></td>
						<td>${dep.assignee}</td>
						<td><a href="${ctx}/activiti/viewTaskForm?taskId=${dep.id }">办理任务</a> <a target="_blank" href="${ctx}/activiti/viewCurrentImage?taskId=${dep.id }">查看当前流程图</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>


</body>
</html>

