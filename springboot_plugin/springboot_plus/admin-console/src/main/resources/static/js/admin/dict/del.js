layui.define(['table', 'dictApi'], function(exports) {
	var dictApi = layui.dictApi;
	var table=layui.table;
	var view = {
			init:function(){
				
			},
			delBatch:function(){
				var data = Common.getMoreDataFromTable(table,"dictTable");
				if(data==null){
					return ;
				}
				Common.openConfirm("确认要删除这些数据字典?",function(){
					var ids =Common.concatBatchId(data,"id");
					dictApi.del(ids,function(){
						Common.info("删除成功");
						dataReload();
					})
				})
				
			}
				
	}
	 exports('del',view);
	
});