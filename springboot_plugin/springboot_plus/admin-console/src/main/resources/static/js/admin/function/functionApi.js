/*访问后台的代码*/
layui.define([], function(exports) {
	var api={
			updateFunction:function(callback){
				Lib.submitForm("/admin/function/update.json",$('#updateForm'),{},callback)
			},
			addFunction:function(callback){
				Lib.submitForm("/admin/function/add.json",$('#addForm'),{},callback)
			},
			del:function(ids,callback){
				Common.post("/admin/function/batchDel.json",{"ids":ids},callback)
			}
			
		
	};
	
	 exports('functionApi',api);
	
});