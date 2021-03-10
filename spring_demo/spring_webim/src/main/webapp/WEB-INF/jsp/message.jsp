<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<title>查看聊天记录</title>
</head>

<body>

	<div class="row">
		<div class="navbar-wrapper">
			<div class="container">

				<table class="table table-bordered table-condensed">
					<thead>
						<tr>
							<td>源</td>
							<td>目标</td>
							<td>时间</td>
							<td>内容</td>
						</tr>
					</thead>
					<tbody>
						<c:if test="${(msgs)!= null && fn:length(msgs) > 0}">
							<c:forEach var="msg" items="${msgs }">
								<tr>
									<td>${msg.ownerjid }</td>
									<td>${msg.withjid }</td>
									<td>${msg.time }</td>
									<td>${msg.body }</td>
								</tr>
							</c:forEach>
						</c:if>
					</tbody>
				</table>


			</div>
		</div>
	</div>

</body>
</html>