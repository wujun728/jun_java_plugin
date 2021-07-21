<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>查看流程图</title>

<link href="<c:url value='/static/jquery/themes/base/jquery-ui.min.css?${version_css}'/>" rel="stylesheet" type="text/css" />
<script src="<c:url value='/static/jquery/ui/jquery-ui.custom.min.js?${version_js}'/>" type="text/javascript"></script>
<script src="<c:url value='/static/jquery/ui/jquery.ui.datepicker.min.js?${version_js}'/>" type="text/javascript"></script>
<script src="<c:url value='/static/jquery/ui/i18n/jquery.ui.datepicker-zh-CN.min.js?${version_js}'/>" type="text/javascript"></script>
</head>
<body>
	<!-- 1.获取到规则流程图 -->
	<img style="position: absolute; top: 0px; left: 0px;" src="${ctx }/activiti/viewImage?deploymentId=${deploymentId }&imageName=${imageName}">

	<!-- 2.根据当前活动的坐标，动态绘制DIV -->
	<div style="position: absolute;border:1px solid red;top:${acs.y }px;left: ${acs.x }px;width: ${acs.width }px;height:${acs.height }px;   "></div>

</body>
</html>

