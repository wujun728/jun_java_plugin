<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>请假申请列表</title>

<link href="<c:url value='/static/jquery/themes/base/jquery-ui.min.css?${version_css}'/>" rel="stylesheet" type="text/css" />

<script src="<c:url value='/static/jquery/ui/jquery-ui.custom.min.js?${version_js}'/>" type="text/javascript"></script>
<script src="<c:url value='/static/jquery/ui/jquery.ui.datepicker.min.js?${version_js}'/>" type="text/javascript"></script>
<script src="<c:url value='/static/jquery/ui/i18n/jquery.ui.datepicker-zh-CN.min.js?${version_js}'/>" type="text/javascript"></script>
</head>
<body>


	<div class="panel panel-primary">
		<!-- Default panel contents -->
		<div class="panel-heading">部署信息管理列表</div>
		<table class="table table-condensed">
			<thead>
				<tr>
					<th>ID</th>
					<th>流程名称</th>
					<th>发布时间</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody id="leaveBillBody">
				<c:forEach var="dep" items="${depList }" varStatus="cur">
					<tr>
						<td>${dep.id }</td>
						<td>${dep.name }</td>
						<td><fmt:formatDate value="${dep.deploymentTime}" type="both" /></td>
						<td><a href="${ctx }/activiti/delDeployment?deploymentId=${dep.id }">删除</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>


	<div class="panel panel-primary">
		<!-- Default panel contents -->
		<div class="panel-heading">流程定义信息列表</div>
		<table class="table table-condensed">
			<thead>
				<tr>
					<th>ID</th>
					<th>名称</th>
					<th>流程定义KEY</th>
					<th>流程定义版本</th>
					<th>流程定义的规则文件名称</th>
					<th>流程定义的规则图片名称</th>
					<th>部署ID</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody id="leaveBillBody">
				<c:forEach var="pd" items="${pdList }" varStatus="cur">
					<tr>
						<td>${pd.id }</td>
						<td>${pd.name }</td>
						<td>${pd.key }</td>
						<td>${pd.version }</td>
						<td>${pd.resourceName }</td>
						<td>${pd.diagramResourceName }</td>
						<td>${pd.deploymentId }</td>
						<td><a target="_blank" href="${ctx }/activiti/viewImage?deploymentId=${pd.deploymentId }&imageName=${pd.diagramResourceName }">查看流程图</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>


	<div class="panel panel-primary">
		<!-- Default panel contents -->
		<div class="panel-heading">部署流程定义</div>
		<br/>
		<form class="form-horizontal" action="${ctx }/activiti/newdeploy" method="post" enctype="multipart/form-data">
			<div class="form-group">
				<label for="filename" class="col-sm-1 control-label">流程名称:</label>
				<div class="col-sm-3">
					<input type="text" class="form-control" id="processDefinitionName" name="processDefinitionName" placeholder="流程名称">
				</div>
			</div>
			<div class="form-group">
				<label for="bpmnZip" class="col-sm-1 control-label">流程文件:</label>
				<div class="col-sm-3">
					<input type="file" id="bpmnZip" name="bpmnZip" placeholder="流程文件">
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-1 col-sm-3">
					<button type="submit" class="btn btn-default">上传流程</button>
				</div>
			</div>
		</form>
	</div>
</body>
</html>

