layui.define([ 'form', 'laydate', 'table','${entity.code}Api'], function(exports) {
    var form = layui.form;
    var ${entity.code}Api = layui.${entity.code}Api;
    var index = layui.index;
    var view = {
        init:function(){
            Lib.initGenrealForm($("#addForm"),form);
            this.initSubmit();
        },
        initSubmit:function(){
            $("#addButton").click(function(){
                 form.on('submit(form)', function(){
                     ${entity.code}Api.add${upperFirst(entity.code)}($('#addForm'),function(){
                         parent.window.dataReload();
                         Common.info("添加成功");
                         Lib.closeFrame();
                     });
                });
            });
        
            $("#addButton-cancel").click(function(){
                Lib.closeFrame();
            });
        }
    			
    }
    exports('add',view);
});