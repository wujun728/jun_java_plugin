layui.define([ 'form', 'laydate', 'table','menuApi'], function(exports) {
	var form = layui.form;
	var menuApi = layui.menuApi;
	var index = layui.index;
	var view = {
			init:function(){
				Lib.initGenrealForm($("#updateForm"),form);
				this.initSubmit();
			},
			initSubmit:function(){
				$("#saveMenu").click(function(){
					//默认值
					if(!$('#parentMenuId').val()){
						$('#parentMenuId').val(0);
					}
					
					menuApi.updateMenu(function(){
						Common.info("更新成功");
						Lib.closeFrame();
						parent.window.dataReload();
					});
					
					
				});
				
				$("#saveMenu-cancel").click(function(){
					Lib.closeFrame();
				});
			}
				
	}
	
	
	
	 exports('edit',view);
	
});