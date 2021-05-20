layui.define([ 'form', 'laydate', 'table','functionApi'], function(exports) {
	var form = layui.form;
	var functionApi = layui.functionApi;
	var index = layui.index;
	var view = {
			init:function(){
				Lib.initGenrealForm($("#addForm"),form);
				this.initSubmit();
			},
			initSubmit:function(){
				$("#saveFunction").click(function(){
					form.on('submit(form)', function(){
						functionApi.addFunction(function(){
							Common.info("添加成功");
							Lib.closeFrame();
						});
						parent.window.dataReload();
					});
				});
				
				$("#saveFunction-cancel").click(function(){
					Lib.closeFrame();
				});
			}
				
	}
	 exports('add',view);
	
});