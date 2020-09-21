<%@ page language="java" pageEncoding="utf-8"%>
<div style="text-align: center;">
	<form id="updateForm" method="post">
		<p><span style="width:80px;font-weight:bold;text-align: right">当前密码：</span><input id="oldPass" name="oldPass" type="password" style="width:150px;"/></p>
		<p><span style="width:80px;font-weight:bold;text-align: right">&nbsp;&nbsp;&nbsp;新密码：</span><input id="newPass" name="newPass" type="password" style="width:150px;"/></p>
		<p><span style="width:80px;font-weight:bold;text-align: right">确认密码：</span><input id="newPassCheck" type="password" style="width:150px;"/></p>
		<p><span style="width:110px;text-align: center"><input id="subBtn" type="button" value="确认"/></span>&nbsp;&nbsp;&nbsp;<span style="width:110px;text-align: center"><input id="cancleUpdate" type="button" value="取消"/></span></p>
	</form>
</div>

<script type="text/javascript">
	$('#subBtn').click(function(){
		if($.trim($('#oldPass').val())==''|| $.trim($('#newPass').val())=='' || $.trim($('#newPassCheck').val()) == ''){
			$.messager.alert('提示','请输入密码','error');
			return;
		}
		if($.trim($('#newPass').val()) != $.trim($('#newPassCheck').val())){
			$.messager.alert('提示','新密码输入不一致','error');
			return;
		}
		var param = $('#updateForm').serialize();
		$('#subBtn').attr('disabled','disabled');
		$('cancleUpdate').attr('disabled','disabled');
		$.ajax({
		  type: "POST",
		   url: "${BASE_PATH}/user/updatePassword.do",
		   data: param,
		   success:function(data){
				$('#subBtn').removeAttr('disabled');
				$('cancleUpdate').removeAttr('disabled');
				data = eval('('+data+')');
				if(data.success){
					$.messager.alert('提示','修改成功,请重新登录','info',function(){
						window.location = 'logout.do';
					});
					$(this).closest('.window-body').dialog('destroy');
					
				}else{
					$.messager.alert('提示',data.msg,'error');
				}
			} 
		});

	});
	$('#cancleUpdate').click(function(){
		$(this).closest('.window-body').dialog('destroy');
	});
</script>