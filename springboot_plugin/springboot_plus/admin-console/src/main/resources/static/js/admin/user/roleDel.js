layui.define(['table', 'userApi'], function(exports) {
	var userApi = layui.userApi;
	var table=layui.table;
	var view = {
			init:function(){
				
			},
			delBatch:function(){
				var data = Common.getMoreDataFromTable(table,"userRoleTable");
				if(data==null){
					return ;
				}
				Common.openConfirm("确认要删除这些角色?",function(){
					var ids =Common.concatBatchId(data);
					userApi.delUserRole(ids,function(){
						Common.info("删除成功");
						dataReload();
					})
				})
				
			}
				
	}
	 exports('roleDel',view);
	
});