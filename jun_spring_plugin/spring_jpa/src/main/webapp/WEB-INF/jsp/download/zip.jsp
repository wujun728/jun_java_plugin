<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="zh-CN">
<head>
<title>zip download</title>
<script type="text/javascript" src="<c:url value='/static/js/download/zip.js'/>"></script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="table-responsive col-sm-10">
				<table class="table">
					<thead>
						<tr>
							<th>序列</th>
							<th>文件名</th>
							<th>下载</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="file" items="${files }" varStatus="cur">
							<tr>
								<td>${cur.index+1 }</td>
								<td><a href="javascript:void(0);" class="downloadfile" data-file-path="${file.absolutePath }"><strong>${file.name }</strong></a></td>
								<td><a href="javascript:void(0);" class="downloadzipfile" data-file-path="${file.absolutePath }"><strong>zip</strong></a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<form id="downloadfile" name="downloadfile" class="hide" method="post" action="${ctx }/download/file" target="downloadFrom">
			<input type="hidden" id="filepath" name="filepath" value="" />
		</form>
		<form id="downloadzip" name="downloadzip" class="hide" method="post" action="${ctx }/download/zip" target="downloadFrom">
			<input type="hidden" id="zippath" name="zippath" value="" />
		</form>
		<iframe name="downloadFrom" style="display: none;"></iframe>
</body>
</html>
