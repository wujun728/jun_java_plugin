/*访问后台的代码*/
layui.define([], function(exports) {
	var api={
			updateMenu:function(callback){
				Lib.submitForm("/admin/menu/update.json",$('#updateForm'),{},callback)
			},
			addMenu:function(callback){
				Lib.submitForm("/admin/menu/save.json",$('#addForm'),{},callback)
			},
			del:function(ids,callback){
				Common.post("/admin/menu/batchDel.json",{"ids":ids},callback)
			}
			
		
	};
	
	 exports('menuApi',api);
	
});