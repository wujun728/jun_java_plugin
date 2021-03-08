var chk_value = [];
var userTableInit = new Object();
var $table = $('#tb_users'),
    selections = [];
var logPageSize = 10;

$(function() {
    // 1.初始化Table
    var oTable = new TableInit();
    oTable.Init();
});

function check(obj) {
    var o = $(obj);
    $("#userAddForm").valid(o);
}

var TableInit = function() {
    // 初始化Table
    userTableInit.Init = function() {
        $('#tb_users').bootstrapTable({
            // url: '../../js/static/specialManagement/specialManagement.json', // 请求后台的URL（*）
            url: ZEUS_BASE_URL + '/specialManagements', // 请求后台的URL（*）
            method: 'get', // 请求方式（*）
            toolbar: '#toolbar', // 工具按钮用哪个容器
            striped: true, // 是否显示行间隔色
            cache: false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true, // 是否显示分页（*）
            sortable: false, // 是否启用排序
            sortOrder: "asc", // 排序方式
            queryParams: userTableInit.queryParams, // 传递参数（*）
            sidePagination: "server", // 分页方式：client客户端分页，server服务端分页（*）
            pageNumber: 1, // 初始化加载第一页，默认第一页
            pageSize: 10, // 每页的记录行数（*）
            pageList: [10, 25, 50, 100], // 可供选择的每页的行数（*）
            //			height : 456,
            uniqueId: "ID", // 每一行的唯一标识，一般为主键列
            search: false, // 是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            strictSearch: false,
            showColumns: false, // 是否显示所有的列
            showRefresh: false, // 是否显示刷新按钮
            minimumCountColumns: 2, // 最少允许的列数
            clickToSelect: false, // 设置true 将在点击行时，自动选择rediobox 和 checkbox
            showToggle: false, // 是否显示详细视图和列表视图的切换按钮
            cardView: false, // 是否显示详细视图
            detailView: false, // 是否显示父子表、
            paginationLoop: true,
            maintainSelected: true,
            responseHandler: function(res) { // res 为后台return的值
                $.each(res.rows, function(i, row) {
                    row.state = $.inArray(row.userUuid, selections) !== -1;
                });
                return res;
            },
            columns: [{
                field: 'state',
                title: '全选',
                checkbox: true,
                align: 'center',
                valign: 'middle'
            }, {
                field: 'zxCode',
                title: '编号'
            }, {
                field: 'zxName',
                title: '名称'
            }, {
                field: 'zxRange',
                title: '范围',
                formatter : function(value, row, index) {
                    if(value == 1){
                        return "全国" ;
                    }else if(value == 2){
                        return "省/直辖市" ;
                    }else if(value == 3){
                        return "区/旗/市" ;
                    }
                }
            }, {
                field: 'zxType',
                title: '类型',
                formatter : function(value, row, index) {
                    if(value == 1){
                        return "事件" ;
                    }else if(value == 2){
                        return "案件" ;
                    }
                }
            }, {
                field: 'zxStatus',
                title: '状态',
                formatter : function(value, row, index) {
                    if(value == 1){
                        return "进行中" ;
                    }else if(value == 2){
                        return "已完成" ;
                    }
                }
            }, {
                field: 'startTime',
                title: '开始时间'
            }]
        });
    };
    $table.on('check.bs.table uncheck.bs.table ' +
        'check-all.bs.table uncheck-all.bs.table',
        function() {
            // save your data, here just save the current page
            selections = getIdSelections();
            // push or splice the selections if you want to save all data selections
        });
    // 得到查询的参数
    userTableInit.queryParams = function(params) {
        logPageSize = params.limit;
        var chkstr = chk_value.join(",");
        var temp = { // 这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            offset: params.offset, // nowpage
            limit: params.limit, // pagesize
        };
        return temp;
    };
    return userTableInit;
};

function getIdSelections() {
    return $.map($table.bootstrapTable('getSelections'), function(row) {
        return row.zxUuid;
    });
}

function responseHandler(res) {
    $.each(res.rows, function(i, row) {
        row.state = $.inArray(row.userUuid, selections) !== -1;
    });
    return res;
}

//"新增"按钮
$(".btn-add").click(function() {
    $("input[name='zxName']").val("");
    $("input[name='startTime']").val("");
    $("input[name='endTime']").val("");
    $(".zxRange option:first").prop("selected", 'selected');
    $(".zxType option:first").prop("selected", 'selected');
    $("#modalAddorEditTitle").html("新增专项");
    ajaxRequest("GET", "/specialManagements/zxCode", function(ajaxRet) {
        $("input[name='zxCode']").val(ajaxRet.data);
    }, undefined,undefined);
    $("#modal").modal("show");
});
//"编辑"按钮
$(".btn-upd").click(function() {
    var idSelections = getIdSelections();
    console.log(idSelections);
    if(idSelections == ""){
        layer.msg('您尚未勾选，请勾选需要进行操作的数据', {
            icon : 2
        });
    }else if(idSelections.length > 1){
        layer.msg('您勾选的数据超过一条，请只选择需要操作的一条数据', {
            icon : 2
        });
    }else{
        ajaxRequest("GET", "/specialManagementsById", function(ajaxRet) {
            $("input[name='zxUuid']").val(ajaxRet.zxUuid);//jquery通过name属性获取html对象并赋值为123
            $("input[name='zxCode']").val(ajaxRet.zxCode);
            $("input[name='zxName']").val(ajaxRet.zxName);
            $(".zxRange").val(ajaxRet.zxRange);
            $(".zxType").val(ajaxRet.zxType);
            $("input[name='startTime']").val(ajaxRet.startTime);
            $("input[name='endTime']").val(ajaxRet.endTime);
        }, {"id": idSelections.toString()},undefined);
        $("#modalAddorEditTitle").html("修改专项");  //修改模态框标题
        $("#modal").modal("show");
    }
});

//"删除"按钮
$(".btn-del").click(function() {
    var idSelections = getIdSelections();
    console.log("getIdSelections:" + idSelections);
    if(idSelections == ""){
        layer.msg('您尚未勾选，请勾选需要进行操作的数据', {
            icon : 2
        });
        return ;
    }
    layer.confirm('请是否确定删除所勾选的数据？', {
        icon: 7,
        title: '提示'
    }, function() {
        layer.closeAll('dialog');
        ajaxRequest("DELETE", "/specialManagements", function(ajaxRet) {
            if(ajaxRet.success) {
                layer.msg(ajaxRet.data, {
                    icon : 1
                });
                $("#tb_users").bootstrapTable('destroy');
                userTableInit.Init();
            }
        }, {"idSelections": idSelections.toString()},undefined);
    });
});
//"新增用户"或"修改用户"的模态框重置
function cancer(){
    var modalTitle = $("#modalAddorEditTitle").html();
    console.log(modalTitle);
    if(modalTitle == "修改专项"){
        $("input[name='zxName']").val("");
        $("input[name='zxRange']").val("");
        $("input[name='zxType']").val("");
        $("input[name='startTime']").val("");
        $("input[name='endTime']").val("");
    }
    $("#modal").modal("hide");
}
function addOrEdit(){
    var modalTitle = $("#modalAddorEditTitle").html();
    var vasd = jQuery.form2json("myForm");
    var method = "";
    console.log(vasd.data);
    if(modalTitle == "新增专项"){
        method = "POST";
    }else if(modalTitle == "修改专项"){
        method = "PATCH";
    }
    ajaxRequest(method, "/specialManagements", function(ajaxRet) {
        if(!ajaxRet.success) {
            layer.alert(ajaxRet.message);
        }else{
            $("#tb_users").bootstrapTable('destroy');
            userTableInit.Init();
            $("#modal").modal("hide");
            layer.alert(ajaxRet.data);
        }
    },undefined ,vasd.data);
}

//"专项结束"按钮
$(".btn-stop").click(function() {
    var idSelections = getIdSelections();
    console.log("getIdSelections:" + idSelections);
    if(idSelections == ""){
        layer.msg('您尚未勾选，请勾选需要进行操作的数据', {
            icon : 2
        });
        return ;
    }
    layer.confirm('请是否确定结束所勾选的数据？', {
        icon: 7,
        title: '提示'
    }, function() {
        layer.closeAll('dialog');
        ajaxRequest("PATCH", "/specialManagements/stop", function(ajaxRet) {
            if(ajaxRet.success) {
                layer.msg(ajaxRet.data, {
                    icon : 1
                });
                $("#tb_users").bootstrapTable('destroy');
                userTableInit.Init();
            }
        }, {"idSelections": idSelections.toString()},undefined);
    });
});