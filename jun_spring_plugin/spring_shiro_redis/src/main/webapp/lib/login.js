var LOGIN = {
	dologin:function(){
		var username = $('#username').val();
		var password=$('#password').val();
		if($.trim(username)=='')
		{
			alert("用户名不能为空！");
			return false;
		}
		if($.trim(password)=='')
		{
			alert("密码不能为空！");
			return false;
		}
		$('#login_form').submit();
	},
	logout:function() {
		$.ajax({
			type: "POST",
			url: cpath + '/user/logout.html',
			data: {},
			error: function (msg) {
				alert(msg + "请求错误");
			},
			success: function (msg) {
				if (msg == 'success') {
					location.reload();
				}
			}
		});
	}
};