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
<script type="text/javascript">
  var _ctx = '${ctx}';
  $(function() {
    $("#leaveBillInput").click(function() {
      document.location.href = _ctx + "/activiti/input";
    });
  });
</script>

</head>
<body>

	<form:form class="form-horizontal">
		<div class="form-group">
			<div class="col-sm-2">
				<button class="btn btn-default" type="button" id="leaveBillInput">请假申请</button>
			</div>
		</div>
	</form:form>
	<div class="row">
		<table class="table table-condensed">
			<thead>
				<tr>
					<th>ID</th>
					<th>请假人ID</th>
					<th>请假天数</th>
					<th>请假事由</th>
					<th>请假备注</th>
					<th>请假时间</th>
					<th>请假状态</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody id="leaveBillBody">
				<c:forEach var="lb" items="${list }" varStatus="cur">
					<tr>
						<td>${lb.id }</td>
						<td>${lb.userId }</td>
						<td>${lb.days }</td>
						<td>${lb.content }</td>
						<td>${lb.remark }</td>
						<td><fmt:formatDate value="${lb.leaveDate}" type="both" /></td>
						<td>${lb.stateName }</td>
						<td><c:choose>
								<c:when test="${lb.state==0 }">
									<a href="${ctx }/activiti/input?id=${lb.id}">修改</a>
									<a href="${ctx }/activiti/delete?id=${lb.id }">删除</a>
									<a href="${ctx }/activiti/startProcess?id=${lb.id }">申请请假</a>
								</c:when>
								<c:when test="${lb.state==1 }">
									<a href="${ctx }/activiti/viewHisComment?id=${lb.id }">查看审核记录</a>
								</c:when>
								<c:otherwise>
									<a href="${ctx }/activiti/delete?id=${lb.id }">删除</a>
									<a href="${ctx }/activiti/viewHisComment?id=${lb.id }">查看审核记录</a>
								</c:otherwise>
							</c:choose></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>

