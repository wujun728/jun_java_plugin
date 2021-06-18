layui.define([ 'form', 'table','userApi'], function(exports) {
	var form = layui.form;
	var userApi = layui.userApi;
	var index = layui.index;
	var view = {
			init:function(){
				Lib.initGenrealForm($("#changePasswordForm"),form);
				this.initSubmit();
			},
			initSubmit:function(){
				$("#savePassword").click(function(){
					userApi.changePassword($('#changePasswordForm'),function(){
						Common.info("密码更改成功");
						Lib.closeFrame();
					});
					
				});
				
				$("#savePassword-cancel").click(function(){
					Lib.closeFrame();
				});
			}
				
	}
	
	
	
	 exports('changePassword',view);
	
});