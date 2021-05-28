layui.define([ 'form','codeApi'], function(exports) {
	var form = layui.form;
	var codeApi = layui.codeApi;

	var view = {
			init:function(){
				this.initSubmit();
			},
			initSubmit:function(){
			    $("#genProject").click(function(){
			        codeApi.genProject($('#projectForm'),function(){
                        Common.info("生成项目成功，请用IDE导入新的项目");
                    });
                    
                    
                });
                
                $("#genProject-cancel").click(function(){
                    Lib.closeFrame();
                });
				
			}
	}
	
	
	
	 exports('project',view);
	
});