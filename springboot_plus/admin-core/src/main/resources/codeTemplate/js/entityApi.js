/*访问后台的代码*/
layui.define([], function(exports) {
    var api={
            update${upperFirst(entity.code)}:function(form,callback){
                Lib.submitForm("/${target.urlBase}/${entity.code}/edit.json",form,{},callback)
            },
            add${upperFirst(entity.code)}:function(form,callback){
                Lib.submitForm("/${target.urlBase}/${entity.code}/add.json",form,{},callback)
            },
            del:function(ids,callback){
                Common.post("/${target.urlBase}/${entity.code}/delete.json",{"ids":ids},function(){
                    callback();
                })
            }
            @if(entity.includeExcel){
            ,
            exportExcel:function(form,callback){
                var formPara = form.serializeJson();
                Common.post("/${target.urlBase}/${entity.code}/excel/export.json", formPara, function(fileId) {
                    callback(fileId);
                })
            }
            @}
		
    };
    exports('${entity.code}Api',api);
});