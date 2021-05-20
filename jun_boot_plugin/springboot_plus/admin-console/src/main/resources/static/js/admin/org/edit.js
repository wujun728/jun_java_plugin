layui.define([ 'form', 'laydate', 'table','orgApi'], function(exports) {
	var form = layui.form;
	var orgApi = layui.orgApi;
	var index = layui.index;
	var view = {
			init:function(){
				Lib.initGenrealForm($("#updateForm"),form);
				this.initSubmit();
			},
			initSubmit:function(){
				$("#saveOrg").click(function(){
					orgApi.updateOrg($("#updateForm"),function(){
					    parent.window.dataReload();
						Common.info("更新成功");
						Lib.closeFrame();
					});
					
				});
				
				$("#saveOrg-cancel").click(function(){
					Lib.closeFrame();
				});
			}
				
	}
	
	
	
	 exports('edit',view);
	
});