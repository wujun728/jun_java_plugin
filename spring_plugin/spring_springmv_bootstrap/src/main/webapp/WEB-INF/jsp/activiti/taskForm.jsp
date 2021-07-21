<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>任务表单</title>
<link href="<c:url value='/static/jquery/themes/base/jquery-ui.min.css?${version_css}'/>" rel="stylesheet" type="text/css" />

<script src="<c:url value='/static/jquery/ui/jquery-ui.custom.min.js?${version_js}'/>" type="text/javascript"></script>
<script src="<c:url value='/static/jquery/ui/jquery.ui.datepicker.min.js?${version_js}'/>" type="text/javascript"></script>
<script src="<c:url value='/static/jquery/ui/i18n/jquery.ui.datepicker-zh-CN.min.js?${version_js}'/>" type="text/javascript"></script>

</head>
<body>


	<div class="panel panel-primary">
		<div class="panel-heading">个人任务管理列表</div>


		<form class="form-horizontal" action="${ctx }/activiti/submitTask" method="post">
			<!-- 请假单ID -->
			<input type="hidden" name="taskId" value="${taskId }" />
			<!-- 任务ID -->
			<input type="hidden" name="id" value="${leaveBill.id }" />
			<div class="form-group">
				<label for="filename" class="col-sm-1 control-label">请假天数:</label>
				<div class="col-sm-3">
					<input class="form-control" name="days" value="${leaveBill.days }" disabled="true" type="text" />
				</div>
			</div>
			<div class="form-group">
				<label for="bpmnZip" class="col-sm-1 control-label">请假原因:</label>
				<div class="col-sm-3">
					<input class="form-control" name="content" value="${leaveBill.content }" disabled="true" type="text" />
				</div>
			</div>
			<div class="form-group">
				<label for="bpmnZip" class="col-sm-1 control-label">请假备注:</label>
				<div class="col-sm-3">
					<input class="form-control" name="remark" value="${leaveBill.remark }" disabled="true" type="text" />
				</div>
			</div>
			<div class="form-group">
				<label for="bpmnZip" class="col-sm-1 control-label">批注:</label>
				<div class="col-sm-3">
					<input class="form-control" name="comment" type="text" />
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-1 col-sm-3">
					<c:if test="${(outcomeList)!= null && fn:length(outcomeList) > 0}">
						<c:forEach items="${outcomeList }" var="outcome">
							<button type="submit" class="btn btn-default">${outcome }</button>
						</c:forEach>
					</c:if>


				</div>
			</div>
		</form>
	</div>

	<c:choose>
		<c:when test="${(commentList)!= null && fn:length(commentList) > 0}">
			<div class="panel panel-primary">
				<div class="panel-heading">显示请假申请的批注信息</div>
				<table class="table table-condensed">
					<thead>
						<tr>
							<td>时间</td>
							<!-- <td>批注人</td> -->
							<td>批注信息</td>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${commentList }" var="c">
							<td><fmt:formatDate value="${c.time }" type="both" /></td>
							<%-- <td>${c.usreId }</td> --%>
							<td>${c.fullMessage }</td>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</c:when>
		<c:otherwise>
			<div class="well">
				<p class="text-success">暂时没有批注信息</p>
			</div>
		</c:otherwise>
	</c:choose>

</body>
</html>

