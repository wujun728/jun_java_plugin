layui.define([ 'form', 'laydate', 'table' ], function(exports) {
    var form = layui.form;
    var laydate = layui.laydate;
    var table = layui.table;
    var ${entity.code}Table = null;
    var view ={
        init:function(){
            this.initTable();
            this.initSearchForm();
            this.initToolBar();
            window.dataReload = function(){
                Lib.doSearchForm($("#searchForm"),${entity.code}Table)
            }
        },
        initTable:function(){
            ${entity.code}Table = table.render({
                elem : '#${entity.code}Table',
                height : Lib.getTableHeight(1),
                cellMinWidth: 100,
                method : 'post',
                url : Common.ctxPath + '/${target.urlBase}/${entity.code}/list.json' // 数据接口
                ,page : Lib.tablePage // 开启分页
                ,limit : 10,
                cols : [ [ // 表头
                    {
                        type : 'checkbox',
                        fixed:'left',
                    },
                @for(attr in entity.list){
            @if(attr.name=="delFlag"){continue;}
                    @if(attr.name=="version"){continue;}
                    @if(attr.name=="attachmentId"){continue;}
                {

                    field : '${isEmpty(attr.dictType)?attr.name:(attr.name+"Text")}', ${isNotEmpty(attr.dictType)?"//数据字典类型为 "+attr.dictType}
                        title : '${attr.displayName}',
                    @if(attrLP.first){
                    fixed:'left',
                        width : 60,
                    @}
                    @if(attr.javaType=="date"){
                    templet:function(d){
                        return Common.getDate(d.${attr.name});
                    }
                    @}
                }${!attrLP.last?","}
            @}

        ] ]

        });

            table.on('checkbox(${entity.code}Table)', function(obj){
                var ${entity.code} = obj.data;
                if(obj.checked){
                    //按钮逻辑Lib.buttonEnable()
                }else{

                }
            })
        },

        initSearchForm:function(){
            Lib.initSearchForm( $("#searchForm"),${entity.code}Table,form);
        },
        initToolBar:function(){
            toolbar = {
                add : function() { // 获取选中数据
                    var url = "/${target.urlBase}/${entity.code}/add.do";
                    Common.openDlg(url,"${entity.displayName}管理>新增");
                },
                edit : function() { // 获取选中数目
                    var data = Common.getOneFromTable(table,"${entity.code}Table");
                    if(data==null){
                        return ;
                    }
                    var url = "/${target.urlBase}/${entity.code}/edit.do?${entity.idAttribute.name}="+data.${entity.idAttribute.name};
                    Common.openDlg(url,"${entity.displayName}管理>"+data.${entity.nameAttribute.name}+">编辑");
                },
                del : function() {
                    layui.use(['del'], function(){
                        var delView = layui.del
                        delView.delBatch();
                    });
                }
                @if(entity.includeExcel){
            ,
                exportDocument : function() {
                    layui.use([ '${entity.code}Api' ], function() {
                        var ${entity.code}Api = layui.${entity.code}Api
                        Common.openConfirm("确认要导出这些${entity.displayName}数据?", function() {
                            ${entity.code}Api.exportExcel($("#searchForm"), function(fileId) {
                                Lib.download(fileId);
                            })
                        })
                    });
                },
                importDocument:function(){
                    var uploadUrl = Common.ctxPath+"/${target.urlBase}/${entity.code}/excel/import.do";
                    //模板,
                    var templatePath= "/${target.urlBase}/${entity.code}/${entity.code}_upload_template.xls";
                    //公共的简单上传文件处理
                    var url = "/core/file/simpleUpload.do?uploadUrl="+uploadUrl+"&templatePath="+templatePath;
                    Common.openDlg(url, "${entity.displayName}管理>上传");
                }
                @}
        };
            $('.ext-toolbar').on('click', function() {
                var type = $(this).data('type');
                toolbar[type] ? toolbar[type].call(this) : '';
            });
        }
    }
    exports('index',view);

});