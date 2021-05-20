/*访问后台的代码*/
layui.define([], function(exports) {
	var api={
			updateDict:function(form,callback){
				Lib.submitForm("/admin/dict/update.json",form,{},callback)
			},
			addDict:function(form,callback){
				Lib.submitForm("/admin/dict/add.json",form,{},callback)
			},
			del:function(ids,callback){
				Common.post("/admin/dict/delete.json",{"ids":ids},function(){
					callback();
				})
			}, 
			exportExcel:function(form,callback){
	            var formPara = form.serializeJson();
	            Common.post("/admin/dict/excel/export.json", formPara, function(fileId) {
	                callback(fileId);
	            })
	        }
		
	};
	
	 exports('dictApi',api);
	
});