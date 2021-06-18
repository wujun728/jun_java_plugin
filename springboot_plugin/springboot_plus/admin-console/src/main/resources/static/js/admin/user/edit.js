layui.define([ 'form', 'laydate', 'table','userApi'], function(exports) {
	var form = layui.form;
	var userApi = layui.userApi;
	var index = layui.index;
	var view = {
			init:function(){
				Lib.initGenrealForm($("#updateForm"),form);
				this.initSubmit();
			},
			initSubmit:function(){
				$("#saveUser").click(function(){
					userApi.updateUser($('#updateForm'),function(){
						parent.window.dataReload();
						Common.info("更新成功");
						Lib.closeFrame();
					});
					
					
				});
				
				$("#saveUser-cancel").click(function(){
					Lib.closeFrame();
				});
			}
				
	}
	
	
	
	 exports('edit',view);
	
});