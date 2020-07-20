<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/taglibs.jsp"%>
<html>
<head>
<title>简约微博</title>
</head>
<body>
<ul class="breadcrumb">
  <li><a href="${base}/index">首页</a> <span class="divider">/</span></li>
  <li class="active">简约微博</li>
</ul>

	<table class="table table-bordered table-striped">
		<thead>
			<tr><th>标题</th><th>内容</th><th>发布时间</th></tr>
		</thead>
		<tbody>
			<c:forEach var="blog" items="${blogs}">
				<tr>
					<td>${blog.title}</td>
					<td>${blog.context}</td>
					<td>${blog.publishDate}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<form name="blog" action="${base}/example/blog/save" method="post" class="well">
		<fieldset>
    		<legend>发布微博</legend>
    		<div class="control-group">
		      <label class="control-label" for="title">标题</label>
		      <div class="controls">
		        <input type="text" class="input-xlarge" id="title" name="title">
		      </div>
		    </div>
		    <div class="control-group">
		      <label class="control-label" for="context">内容</label>
		      <div class="controls">
		      	<textarea name="context" id="context" class="input-xlarge"></textarea>
		      </div>
		    </div>
			<p><input type="submit" value="发布" class="btn btn-primary btn-large"/></p>
		</fieldset>
	</form>
</body>
</html>