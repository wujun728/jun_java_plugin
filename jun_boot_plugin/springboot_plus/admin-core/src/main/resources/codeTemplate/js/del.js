layui.define(['table', '${entity.code}Api'], function(exports) {
    var ${entity.code}Api = layui.${entity.code}Api;
    var table=layui.table;
    var view = {
        init:function(){
        },
        delBatch:function(){
            var data = Common.getMoreDataFromTable(table,"${entity.code}Table");
            if(data==null){
                return ;
            }
            Common.openConfirm("确认要删除这些${entity.displayName}?",function(){
            var ids =Common.concatBatchId(data,"${entity.idAttribute.name}");
            ${entity.code}Api.del(ids,function(){
                Common.info("删除成功");
                    dataReload();
                })
            })
        }
    }
    exports('del',view);
	
});