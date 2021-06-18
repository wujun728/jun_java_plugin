layui.define([ 'form', 'laydate', 'table','dictApi'], function(exports) {
	var form = layui.form;
	var dictApi = layui.dictApi;
	var index = layui.index;
	var view = {
			init:function(){
				Lib.initGenrealForm($("#addForm"),form);
				this.initSubmit();
			},
			initSubmit:function(){
				$("#addButton").click(function(){
					dictApi.addDict($("#addForm"),function(){
					    parent.window.dataReload();
						Common.openConfirm("添加成功，继续添加？",null,function(){
						    Lib.closeFrame();
						})
						
						
					});
					
					
				});
				
				$("#addButton-cancel").click(function(){
					Lib.closeFrame();
				});
			}
				
	}
	 exports('add',view);
});