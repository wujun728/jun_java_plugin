layui.define([ 'form', 'laydate', 'table','roleApi'], function(exports) {
	var form = layui.form;
	var roleApi = layui.roleApi;
	var index = layui.index;
	var view = {
			init:function(){
				Lib.initGenrealForm($("#updateForm"),form);
				this.initSubmit();
			},
			initSubmit:function(){
				$("#updateButton").click(function(){
					roleApi.updateRole($("#updateForm"),function(){
						parent.window.dataReload();
						Common.info("更新成功");
						Lib.closeFrame();
					});
					
					
				});
				
				$("#updateButton-cancel").click(function(){
					Lib.closeFrame();
				});
			}
				
	}
	
	
	
	 exports('edit',view);
	
});