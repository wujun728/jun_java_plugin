layui.define([ 'form', 'laydate', 'table','userApi'], function(exports) {
	var form = layui.form;
	var userApi = layui.userApi;
	var index = layui.index;
	var view = {
			init:function(){
				this.initSubmit();
			},
			initSubmit:function(){
				$("#saveUserRole").click(function(){
					userApi.addUserRole($('#userRoleAddForm'),function(){
						parent.window.dataReload();
						Common.info("添加成功");
						Lib.closeFrame();
					});
					
					
				});
				
				$("#saveUserRole-cancel").click(function(){
					Lib.closeFrame();
				});
			}
				
	}
	 exports('userRoleAdd',view);
	
});