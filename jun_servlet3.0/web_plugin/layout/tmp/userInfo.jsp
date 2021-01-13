<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="${request.getContextPath()}/layout/meta.jsp"></jsp:include>
<jsp:include page="${request.getContextPath()}/layout/easyui.jsp"></jsp:include>
<script type="text/javascript" charset="utf-8">
	var userInfoForm;
	$(function() {
		userInfoForm = $('#userInfoForm').form({
			url : 'userController.do?editUserInfo',
			success : function(data) {
				var d = $.parseJSON(data);
				if (d && d.success) {
					$.messager.show({
						msg : '密码修改成功！',
						title : '提示'
					});
					userInfoForm.find('[name=password],[name=oldPassword]').val('');
				} else {
					$.messager.show({
						msg : '密码修改失败，原密码错误！',
						title : '提示'
					});
				}
			}
		});

		$.ajax({
			url : 'userController.do?getUserInfo',
			data : {},
			cache : false,
			dataType : "json",
			type : "POST",
			success : function(r) {
				if (r.success) {
					var user = r.obj;
					$('#roleNames').html(user.roleText);
					$('#resourcesNames').html(user.resourcesText);

					userInfoForm.form('load', user);
					userInfoForm.find('[name=password]').val('');
				} else {
					$.messager.alert('提示', r.msg, 'error');
				}
			}
		});
	});
</script>
</head>
<body class="easyui-layout">
	<div region="center" border="false">
		<div class="easyui-panel" title="角色" border="false">
			<div id="roleNames" style="padding: 5px;"></div>
		</div>
		<div class="easyui-panel" title="资源">
			<div id="resourcesNames" style="padding: 5px;"></div>
		</div>
		<div class="easyui-panel" title="修改密码">
			<div>
				<form id="userInfoForm" method="post">
					<table class="tableForm">
						<tr>
							<th>用户ID</th>
							<td><input name="id" readonly="readonly" /></td>
							<th>登录名称</th>
							<td><input name="name" readonly="readonly" /></td>
						</tr>
						<tr>
							<th>创建时间</th>
							<td><input name="createdatetime" readonly="readonly" style="width: 150px;" /></td>
							<th>最后修改时间</th>
							<td><input name="modifydatetime" readonly="readonly" style="width: 150px;" /></td>
						</tr>
						<tr>
							<th>原密码</th>
							<td><input name="oldPassword" type="password" style="width: 150px;" /></td>
							<th>新密码</th>
							<td><input name="password" type="password" style="width: 150px;" /></td>
						</tr>
						<tr>
							<td colspan="4" align="right"><a onclick="userInfoForm.submit();" class="easyui-linkbutton" href="javascript:void(0);">修改密码</a>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
</body>
</html>