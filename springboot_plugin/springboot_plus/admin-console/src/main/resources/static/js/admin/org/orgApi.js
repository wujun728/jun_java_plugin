/*访问后台的代码*/
layui.define([], function(exports) {
	var api={
			updateOrg:function(form,callback){
				Lib.submitForm("/admin/org/update.json",form,{},callback)
			},
			addOrg:function(form,callback){
				Lib.submitForm("/admin/org/save.json",form,{},callback)
			},
			del:function(ids,callback){
				Common.post("/admin/org/delete.json",{"ids":ids},function(){
					callback();
				})
			}
			
		
	};
	
	 exports('orgApi',api);
	
});