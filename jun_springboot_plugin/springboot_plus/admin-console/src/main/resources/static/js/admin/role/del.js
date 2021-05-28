layui.define(['table', 'roleApi'], function(exports) {
	var roleApi = layui.roleApi;
	var table=layui.table;
	var view = {
			init:function(){
				
			},
			delBatch:function(){
				var data = Common.getMoreDataFromTable(table,"roleTable");
				if(data==null){
					return ;
				}
				Common.openConfirm("确认要删除这些角色?",function(){
					var ids =Common.concatBatchId(data);
					roleApi.del(ids,function(){
						Common.info("删除成功");
						dataReload();
					})
				})
				
			}
				
	}
	 exports('del',view);
	
});