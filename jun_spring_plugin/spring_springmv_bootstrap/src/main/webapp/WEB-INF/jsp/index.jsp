<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>-首页</title>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="application" />
<link href="<c:url value='/static/jquery/themes/base/jquery-ui.min.css?${version_css}'/>" rel="stylesheet" type="text/css" />
</head>

<body>
	<div class="row">
		<div class="jumbotron">
			<ul>
				<li>Activiti5工作流示例</li>
				<li>Shiro集成</li>
				<li>Springmvc Mybatis集成</li>
				<li>log4j2集成</li>
				<li>Bootstrap集成</li>
				<li>Sitemesh3集成</li>
				<li>文件上传示例</li>
				<li>文件下载示例</li>
				<li>JQuery联想搜索示例</li>
			</ul>
		</div>
	</div>
</body>
</html>
