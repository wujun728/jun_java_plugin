<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/taglibs.jsp"%>
<html>
<head>
<title>团队建设</title>
</head>
<body>
<ul class="breadcrumb">
  <li><a href="${base}/index">首页</a> <span class="divider">/</span></li>
  <li class="active">团队建设</li>
</ul>
	<table class="table table-bordered table-striped">
		<thead>
			<tr><th>编号</th><th>团队名称</th></tr>
		</thead>
		<tbody>
			<c:forEach var="team" items="${teams}">
				<tr>
					<td>${team.id}</td>
					<td>${team.name}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<form name="team" action="${base}/example/team/save" method="post" class="well">
		<fieldset>
    		<legend>团队建设</legend>
    		<div class="control-group">
		      <label class="control-label" for="input01">团队名称</label>
		      <div class="controls">
		        <input type="text" class="input-xlarge" name="name" id="input01">
		      </div>
		    </div>
			<p><input type="submit" value="创建团队" class="btn btn-primary btn-large"/></p>
		</fieldset>
	</form>
</body>
</html>