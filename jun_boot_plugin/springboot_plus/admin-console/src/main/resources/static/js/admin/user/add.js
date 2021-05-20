layui.define([ 'form', 'laydate', 'table','userApi'], function(exports) {
	var form = layui.form;
	var userApi = layui.userApi;
	var index = layui.index;
	var view = {
			init:function(){
				Lib.initGenrealForm($("#addForm"),form);
				this.initSubmit();
			},
			initSubmit:function(){
				$("#saveUser").click(function(){
					userApi.addUser($('#addForm'),function(){
						parent.window.dataReload();
						Common.info("添加成功");
						Lib.closeFrame();
					});
					
					
				});
				
				$("#saveUser-cancel").click(function(){
					Lib.closeFrame();
				});
			}
				
	}
	 exports('add',view);
	
});