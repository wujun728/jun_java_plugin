/*访问后台的代码*/
layui.define([], function(exports) {
	var api={
			updateRole:function(form,callback){
				Lib.submitForm("/admin/role/update.json",form,{},callback)
			},
			addRole:function(form,callback){
				Lib.submitForm("/admin/role/add.json",form,{},callback)
			},
			del:function(ids,callback){
				Common.post("/admin/role/delete.json",{"ids":ids},function(){
					callback();
				})
			},
			queryFunctionByRole:function(roleId,callback){
				Common.post("/admin/role/function/ids.json",{"roleId":roleId},function(data){
					callback(data);
				})
			},
			saveFunctions:function(roleId,ids){
				Common.post("/admin/role//function/update.json",{"roleId":roleId,"ids":ids},function(data){
					Common.info("保存功能成功")
				})
			},
			saveDataAccess:function(roleId,fnId,accessType){
				Common.post("/admin/role/function/updateDataAccess.json",{"roleId":roleId,"fnId":fnId,"accessType":accessType},function(data){
					Common.info("保存数据权限成功")
				})
			}
			
		
	};
	
	 exports('roleApi',api);
	
});