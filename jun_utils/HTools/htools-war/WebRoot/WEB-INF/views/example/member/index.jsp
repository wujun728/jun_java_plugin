<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/taglibs.jsp"%>
<html>
<head>
<title>成员管理</title>
</head>
<body>
<ul class="breadcrumb">
  <li><a href="${base}/index">首页</a> <span class="divider">/</span></li>
  <li class="active">成员管理</li>
</ul>
		<table class="table table-bordered table-striped">
			<thead>
				<tr><th>号码</th><th>姓名</th><th>队内位置</th><th>是否队长</th><th>团队名称</th></tr>
			</thead>
			<tbody>
				<c:forEach var="member" items="${page.content}">
					<tr>
						<td>${member.id}</td>
						<td>${member.name}</td>
						<td>${member.type}</td>
						<td><c:if test="${member.teamLeader==false}">普通球员</c:if>
						<c:if test="${member.teamLeader==true}">队长</c:if></td>
						<td>${member.team.name}</td>
					</tr>
				</c:forEach>
			</tbody>
			<tr>
				<td colspan="5" align="right">
					<div class="pagination">
					  <ul>
							<li class="disabled"><a href="#">第<c:if test="${page.totalPages-1>0}">${page.number+1}</c:if><c:if test="${page.totalPages-1<=0}">0</c:if>页, 共${page.totalPages}页</a></li> 
							<c:set var="actionName" value="${base}/example/member/page"></c:set>
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
					  </ul>
					</div>
				</td>
			</tr>
		</table>

		<form name="member" action="${base}/example/member/save" method="post" class="well">
			<fieldset>
	    		<legend>注册球员</legend>
	    		<div class="control-group">
			      <label class="control-label" for="name">姓名</label>
			      <div class="controls">
			        <input type="text" class="input-xlarge" name="name" id="name">
			      </div>
			    </div>
			    <div class="control-group">
			      <label class="control-label" for="type">位置</label>
			      <div class="controls">
			      	<select name="type" id="type">
						<option>前锋</option>
						<option>影锋</option>
						<option>边锋</option>
						<option>中锋</option>
						<option>中前位</option>
						<option>边前位</option>
						<option>边后卫</option>
						<option>中后卫</option>
						<option>守门员</option>
					</select>
			      </div>
			    </div>
			     <div class="control-group">
			      <label class="control-label" for="teamLeader">是否队长</label>
			      <div class="controls">
			      	<select name="teamLeader" id="teamLeader">
						<option value="false">普通球员</option>
						<option value="true">队长</option>
					</select>
			      </div>
			    </div>
			    <div class="control-group">
			      <label class="control-label" for="teamId">选择球队</label>
			      <div class="controls">
			      	<select name="team.id" id="teamId">
						<c:forEach var="team" items="${teams}">
							<option value="${team.id}">${team.name}</option>
						</c:forEach>
					</select>
			      </div>
			    </div>
				<p><input type="submit" value="申请入队" class="btn btn-primary btn-large"/></p>
			</fieldset>
		</form>
</body>
</html>