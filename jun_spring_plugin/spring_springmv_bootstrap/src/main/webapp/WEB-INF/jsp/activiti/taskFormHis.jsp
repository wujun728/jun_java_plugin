<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>请假任务办理</title>
</head>
<body>
	<div class="panel panel-primary">
		<div class="panel-heading">请假申请的任务办理</div>
		<br/>
		<form action="${ctx }/activiti/save" id="leaveBill" method="post" class="form-horizontal">
			<div class="form-group">
				<label for="title" class="col-sm-2 control-label">请假天数：</label>
				<div class="col-sm-3">
					<input type="text" class="form-control" name="days" id="days" value="${leaveBill.days }" disabled="disabled"/>
				</div>
			</div>
			<div class="form-group">
				<label for="content" class="col-sm-2 control-label">请假原因：</label>
				<div class="col-sm-3">
					<textarea class="form-control" name="content" id="content" row="5" disabled="disabled">${leaveBill.content }</textarea>
				</div>
			</div>
			<div class="form-group">
				<label for="remark" class="col-sm-2 control-label">请假备注：</label>
				<div class="col-sm-3">
					<textarea class="form-control" name="remark" id="remark" row="5" disabled="disabled">${leaveBill.remark }</textarea>
				</div>
			</div>
			<div class="form-group input-group-sm">
				<label class="col-sm-2 control-label">&nbsp;</label>
				<div class="col-sm-3">
					<button class="btn btn-default" type="button" onclick="javascript:history.go(-1);">返回</button>
				</div>
			</div>
		</form>
	</div>

	<c:choose>
		<c:when test="${(commentList)!= null && fn:length(commentList) > 0}">
			<div class="panel panel-primary">
				<div class="panel-heading">请假申请的任务办理</div>
				<table class="table table-condensed">
					<tr class="success">
						<th>时间</th>
						<th>批注人</th>
						<th>批注信息</th>
					</tr>
					<c:forEach items="${commentList}" var="com">
						<tr>
							<td><fmt:formatDate value="${com.time}" type="both" /></td>
							<td>${com.userId }</td>
							<td>${com.fullMessage }</td>
						</tr>
					</c:forEach>
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