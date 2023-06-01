<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="row">
	<div class="col-md-8">
		<form class="form-inline" role="form" action="${ctx}/job/index" method="POST">
			<div class="form-group">
				<label class="sr-only" for="triggerName">Trigger 名称</label> <input type="text" class="form-control" id="triggerName" name="triggerName" value="${triggerName }" placeholder="Trigger 名称" />
			</div>
			<div class="form-group">
				<label class="sr-only" for="triggerGroup">Trigger 分组</label> <select class="form-control" name="triggerGroup">
					<option value=""></option>
					<c:forEach var="tg" items="${servers }">
						<c:if test="${empty triggerGroup }">
							<option value="${tg }">${tg}</option>
						</c:if>
						<c:if test="${not empty triggerGroup && triggerGroup eq tg }">
							<option value="${tg }" selected="selected">${tg}</option>
						</c:if>
					</c:forEach>
				</select>

			</div>
			<button type="submit" class="btn btn-info">查询</button>
		</form>
	</div>
</div>
<br />
<div class="table-responsive">
	<table class="table table-striped table-hover">
		<thead>
			<tr class="info">
				<th>Trigger 名称</th>
				<th>Trigger 分组</th>
				<th>下次执行时间</th>
				<th>上次执行时间</th>
				<th>优先级</th>
				<th>Trigger 状态</th>
				<th>Trigger 类型</th>
				<th>开始时间</th>
				<th>结束时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="t" items="${list }">
				<tr>
					<td>${t.triggerName}</td>
					<td>${t.triggerGroup}</td>
					<td><fmt:formatDate pattern="yyyy-MM-dd" value="${t.nextFireTime}" /></td>
					<td><fmt:formatDate pattern="yyyy-MM-dd" value="${t.prevFireTime}" /></td>
					<td>${t.priority}</td>
					<td>${t.state}</td>
					<td>${t.triggerType}</td>
					<td><fmt:formatDate pattern="yyyy-MM-dd" value="${t.startTime}" /></td>
					<td><fmt:formatDate pattern="yyyy-MM-dd" value="${t.endTime}" /></td>
					<td>
						<button type="button" class="btn btn-info btn-xs" id="pause" data-name="${t.triggerName}" data-group="${t.triggerGroup}">暂停</button>
						<button type="button" class="btn btn-info btn-xs" id="resume" data-name="${t.triggerName}" data-group="${t.triggerGroup}">恢复</button>
						<button type="button" class="btn btn-info btn-xs" id="remove" data-name="${t.triggerName}" data-group="${t.triggerGroup}">删除</button>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<script type="text/javascript">
  var currentPage = "console";
</script>
<script src="${ctx}/static/js/job/job.js?${version_js}"></script>
