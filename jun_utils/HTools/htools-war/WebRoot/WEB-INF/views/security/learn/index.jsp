<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/static/taglibs.jsp"%>
<html>
<head>
<title>知识库维护</title>
</head>
<body>
<ul class="breadcrumb">
  <li><a href="${base}/index">首页</a> <span class="divider">/</span></li>
  <li class="active">知识库维护</li>
</ul>
	<form action="${base}/security/learn/upload" method="post" enctype="multipart/form-data" class="well">
		<p><input type="file" name="file" id="file" class="input-xlarge uneditable-input"></p>
		<p>
			<input type="submit" value="上传" class="btn btn-primary btn-large" data-loading-text="提交中..."/>
			<a href="#learnModal" role="button" class="btn btn-warning btn-large" data-toggle="modal" data-placement="bottom" title="预览当前知识库" onclick="return getLearnValue();">预览当前知识库</a>
		</p>
	</form>
</body>
</html>