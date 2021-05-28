layui.define([ 'form', 'laydate', 'table','orgApi'], function(exports) {
	var form = layui.form;
	var orgApi = layui.orgApi;
	var index = layui.index;
	var view = {
			init:function(){
				Lib.initGenrealForm($("#addForm"),form);
				this.initSubmit();
			},
			initSubmit:function(){
				$("#saveOrg").click(function(){
					orgApi.addOrg($('#addForm'),function(){
						parent.window.dataReload();
						Common.info("添加成功");
						Lib.closeFrame();
					});
					
					
				});
				
				$("#saveOrg-cancel").click(function(){
					Lib.closeFrame();
				});
			}
				
	}
	 exports('add',view);
	
});