<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/taglibs.jsp"%>
<html>
<head>
<title>登录账户维护列表</title>
</head>
<body>
<ul class="breadcrumb">
  <li><a href="${base}/index">首页</a> <span class="divider">/</span></li>
  <li class="active">登录账户维护列表</li>
</ul>
		<table class="table table-bordered table-striped table-hover">
			<thead>
				<tr><th>序号</th><th>登录名</th><th>姓名</th><th>电子邮箱</th><th>手机号码</th><th>账户状态</th><th>维护</th></tr>
			</thead>
			<tbody>
				<c:forEach var="user" items="${page.content}" varStatus="index">
					<tr>
						<td>${index.index+1}</td>
						<td>${user.loginName}</td>
						<td>${user.name}</td>
						<td>${user.email}</td>
						<td>${user.mobile}</td>
						<td>
						<c:if test="${user.status=='disabled'}"><span class="label">已冻结</span></c:if>
						<c:if test="${user.status!='disabled'}"><span class="label label-info">活动中</span></c:if>
						</td>
						<td>
							<div class="btn-group">
					          <button class="btn dropdown-toggle" data-toggle="dropdown">操作 <span class="caret"></span></button>
					          <ul class="dropdown-menu">
					            <li><a href="${base}/security/user/input/${user.id}"><i class="icon-pencil"></i>修改</a></li>
					            <li><a href="${base}/security/user/delete/${user.id}"><i class="icon-trash"></i>删除</a></li>
					            <li class="divider"></li>
					            <li><a href="${base}/security/user/enabled/${user.id}"><i class="icon-ok-sign"></i>激活</a></li>
					            <li><a href="${base}/security/user/disabled/${user.id}"><i class="icon-remove-sign"></i>冻结</a></li>
					          </ul>
					        </div>
						</td>
					</tr>
				</c:forEach>
			</tbody>
			<tr>
				<td colspan="7" align="right">
					<div class="pagination">
					  <ul>
							<li class="disabled"><a href="#">第<c:if test="${page.totalPages-1>0}">${page.number+1}</c:if><c:if test="${page.totalPages-1<=0}">1</c:if>页, 共${page.totalPages-1<=0?1:page.totalPages}页</a></li> 
							<c:set var="actionName" value="${base}/security/user/page"></c:set>
							<c:if test="${page.totalPages-1>0}">
								<li><a href="${actionName}/0">首页</a></li> 
							</c:if>
							<c:if test="${page.totalPages-1<=0}">
								<li class="disabled"><a href="#">首页</a></li>
							</c:if>
							<c:if test="${page.number>0}">
								<li><a href="${actionName}/${page.number-1}">上一页</a></li> 
							</c:if>
							<c:if test="${page.number<=0}">
								<li class="disabled"><a href="#">上一页</a></li>
							</c:if>
							<c:if test="${page.totalPages>0&&page.number<page.totalPages-1}">
								<li><a href="${actionName}/${page.number+1}">下一页</a></li> 
							</c:if>
							<c:if test="${page.totalPages<=0||page.number>=page.totalPages-1}">
								<li class="disabled"><a href="#">下一页</a></li>
							</c:if>
							<c:if test="${page.totalPages-1<=0}">
								<li class="disabled"><a href="#">末页</a></li>
							</c:if>
							<c:if test="${page.totalPages-1>0}">
								<li><a href="${actionName}/${page.totalPages-1}">末页</a></li> 
							</c:if>
							&nbsp;&nbsp;<a class="btn btn-success" href="${base}/security/user/input/0">添加新账户</a>
					  </ul>
					</div>
				</td>
			</tr>
		</table>
</body>
</html>