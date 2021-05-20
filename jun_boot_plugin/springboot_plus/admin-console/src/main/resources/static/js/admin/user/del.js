layui.define(['table', 'userApi'], function(exports) {
	var userApi = layui.userApi;
	var table=layui.table;
	var view = {
			init:function(){
				
			},
			delBatch:function(){
				var data = Common.getMoreDataFromTable(table,"userTable");
				if(data==null){
					return ;
				}
				Common.openConfirm("确认要删除这些用户?",function(){
					debugger;
					var ids =Common.concatBatchId(data);
					userApi.del(ids,function(){
						Common.info("删除成功");
						dataReload();
					})
				})
				
			}
				
	}
	 exports('del',view);
	
});