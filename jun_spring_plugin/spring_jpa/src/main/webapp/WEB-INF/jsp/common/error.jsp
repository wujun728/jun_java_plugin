<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<title>错误<sitemesh:title /></title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="${ctx}/static/bootstrap/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/static/bootstrap/css/bootstrap-theme.min.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/static/css/default.css" type="text/css" rel="stylesheet" />

<!--[if lt IE 9]>
<script src="${ctx}/static/bootstrap/js/html5shiv.js"></script>
<script src="${ctx}/static/bootstrap/js/respond.min.js"></script>
<![endif]-->
<body class="preview" id="top" data-spy="scroll" data-target=".subnav" data-offset="80">
	<div class="container" id="mainContent">
		<div class="row">
			<div class="span3">&nbsp;</div>
			<div class="span5">
				<div class="wel1">
					<legend> 页面访问出错。 </legend>
					<c:catch var="err">
					${exception.code } :<s:message code="${exception.code}" text="System error." />
					</c:catch>
					<c:if test="${err != null }">
								错误消息:<font color="red"> ${exception.message }</font>
					</c:if>
				</div>
				<div class="span3">&nbsp;</div>
			</div>
		</div>
	</div>
</body>
</html>
