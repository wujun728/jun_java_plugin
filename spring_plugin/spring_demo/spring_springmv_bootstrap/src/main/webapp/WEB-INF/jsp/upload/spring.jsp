<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="zh-CN">
<head>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<title>spirng file upload</title>
<script type="text/javascript" src="<c:url value='/static/jquery/plugins/js/ajaxfileupload.js'/>"></script>
<script type="text/javascript" src="<c:url value='/static/js/upload/ajax.js'/>"></script>
</head>
<body>
	<div class="container">
		<div class="row">
			<form role="form" action="${ctx }/upload/spring" method="post" enctype="multipart/form-data">
				<div class="form-group">
					<label for="exampleInputFile">文件</label> <input type="file" id="uploadfile" name="uploadfile">
					<p class="help-block">目前没有对文件类型进行限制</p>
				</div>
				<button type="submit" class="btn btn-default">上传</button>
			</form>
			${msg }
		</div>
</body>
</html>
