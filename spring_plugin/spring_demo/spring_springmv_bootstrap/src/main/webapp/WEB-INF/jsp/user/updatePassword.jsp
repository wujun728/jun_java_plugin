<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>创建条目</title>
<script src="<c:url value='/static/js/common.js?${version_js}'/>" type="text/javascript"></script>
<script src="<c:url value='/static/js/user/updatePassword.js?${version_js}'/>" type="text/javascript"></script>
</head>

<body>
	<form:form action="${ctx }/user/updatepassword.htm" commandName="userCommand" id="updatePasswordForm" method="post" class="form-horizontal">
		<div class="form-group">
			<label class="col-sm-2 control-label">密码：</label>
			<div class="col-sm-4">
				<input type="password" class="form-control" name="password" id="password" maxlength="12" />
			</div>
			<form:errors cssClass="label label-danger" path="password"></form:errors>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">新密码：</label>
			<div class="col-sm-4">
				<input type="password" class="form-control" name="newPassword" id="newPassword" maxlength="12" />
			</div>
			<form:errors cssClass="label label-danger" path="newPassword"></form:errors>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">确认密码：</label>
			<div class="col-sm-4">
				<input type="password" class="form-control" name="passwordAgain" id="passwordAgain" maxlength="12" />
			</div>
			<form:errors cssClass="label label-danger" path="passwordAgain"></form:errors>
		</div>
		<div class="form-group input-group-sm">
			<label class="col-sm-2 control-label">&nbsp;</label>
			<div class="col-sm-2">
				<button class="btn btn-default active" type="button" id="updatePasswordBtn">保存</button>
			</div>
		</div>
	</form:form>
</body>
</html>

