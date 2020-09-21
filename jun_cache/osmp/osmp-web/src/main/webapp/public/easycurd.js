/**
 * 重载表格
 * 
 * @param {Object}
 *            gridName
 * @param {Object}
 *            gridType
 */
function reloadGrid(gridName, gridType) {
    if (gridType == 'treegrid') {
        $('#' + gridName).treegrid('reload');
    } else {
        $('#' + gridName).datagrid('reload');
    }

}

/**
 * 刪除请求
 * 
 * @param url
 *            删除提交的URL
 * @param gridName
 *            列表名称
 * @param gridType
 *            列表类型 treegrid datagrid
 */
function Del(url, gridName, gridType) {
    $.messager.confirm("确认", "确定要删除吗?", function(r) {
        if (r) {
            $.ajax({
                url : url,
                dataType : 'json',
                success : function(result) {
                    if (result.success) {
                        reloadGrid(gridName, gridType);
                        $.messager.show({
                            title : '提示',
                            msg : '删除数据成功!'
                        });
                    } else {
                        $.messager.show({
                            title : '提示',
                            msg : '删除数据失败!'
                        });
                    }
                }
            });
        }
    });
}

/**
 * 编辑请求
 * 
 * @param {Object}
 *            pageUrl 跳转到编辑页面的URL
 * @param {Object}
 *            gridName 数据表格名称
 * @param {Object}
 *            gridType 数据表格类型 treegrid datagrid
 * @param {Object}
 *            width 弹出窗口宽度
 * @param {Object}
 *            height 弹出窗口高度
 * @param {Object}
 *            formName 编辑页面FORM名称
 * @param {Object}
 *            postUrl 编辑提交的URL
 * @memberOf {TypeName}
 */
function Edit(pageUrl, gridName, gridType, width, height, formName, postUrl) {
    $('<div/>').dialog({
        href : pageUrl,
        width : width,
        height : height,
        modal : true,
        title : '编辑',
        buttons : [ {
            text : '提交编辑',
            iconCls : 'icon-edit',
            handler : function() {
                var d = $(this).closest('.window-body');
                $('#' + formName).form('submit', {
                    url : postUrl,
                    success : function(result) {
                        try {
                            var r = $.parseJSON(result);
                            if (r.success) {
                                reloadGrid(gridName, gridType);
                                d.dialog('destroy');
                                $.messager.alert("操作提示", r.msg ? r.msg : '编辑数据成功!', "info");
                            } else {
                                $.messager.alert("操作提示", r.msg ? r.msg : '编辑数据失败!', "error");
                            }
                        } catch (e) {
                            $.messager.alert('提示', result);
                        }
                    }
                });
            }
        } ],
        onClose : function() {
            $(this).closest('.window-body').dialog('destroy');
        }
    });

}
/**
 * 窗口请求
 * 
 * @param {Object}
 *            pageUrl 跳转到编辑页面的URL
 * @param {Object}
 *            width 弹出窗口宽度
 * @param {Object}
 *            height 弹出窗口高度
 * @memberOf {TypeName}
 */
function OpenPlainWindow(pageUrl, width, height, title, winid) {
    $('<div/>').dialog({
        href : pageUrl,
        id : winid || 'No',
        width : width,
        height : height,
        title : title || 'window',
        modal : true,
        cache : false,
        onClose : function() {
            $(this).closest('.window-body').dialog('destroy');
        }
    });

}

/**
 * 
 * @param {Object}
 *            pageUrl 页面跳转url
 * @param {Object}
 *            gridName 列表名称
 * @param {Object}
 *            gridType 列表类型 treegrid datagrid
 * @param {Object}
 *            width 弹窗宽度
 * @param {Object}
 *            height 弹窗高度
 * @param {Object}
 *            formName 新增界面form名称
 * @param {Object}
 *            postUrl 新增提交的URL
 * @memberOf {TypeName}
 */
function Add(pageUrl, gridName, gridType, width, height, formName, postUrl) {
    $('<div/>').dialog({
        href : pageUrl,
        width : width,
        height : height,
        modal : true,
        title : '添加数据',
        buttons : [ {
            text : '新增',
            iconCls : 'icon-edit',
            handler : function() {
                var d = $(this).closest('.window-body');
                $('#' + formName).form('submit', {
                    url : postUrl,
                    success : function(result) {
                        try {
                            var r = $.parseJSON(result);
                            if (r.success) {
                                reloadGrid(gridName, gridType);
                                d.dialog('destroy');
                                $.messager.alert("操作提示", r.msg ? r.msg : '新增数据成功!', "info");
                            } else {
                                $.messager.alert("操作提示", r.msg ? r.msg : '新增数据失败!', "error");
                            }
                        } catch (e) {
                            alert(result);
                            $.messager.alert('提示', result);
                        }
                    }
                });
            }
        } ],
        onClose : function() {
            $(this).closest('.window-body').dialog('destroy');
        }
    });
}

$(function() {
    $.messager.defaults = {
        ok : "确定",
        cancel : "取消"
    };
});

/**
 * 執行保存
 * 
 * @param url
 * @param gridname
 */
function saveDeclare(url) {
    $('#fm').form('submit', {
        url : url,
        onSubmit : function() {
            return $(this).form('validate');
        },
        success : function(r) {
            $("#test").dialog("close");
            // reloadTable();

        }
    });
}

/**
 * 添加
 * 
 * @param title
 * @param addurl
 * @param saveurl
 * @param id
 * @param gridname
 */
function add(title, addurl, saveurl) {
    openaddorupwin(title, addurl, saveurl);
}
/**
 * 更新
 * 
 * @param title
 * @param addurl
 * @param saveurl
 * @param id
 * @param gridname
 */
function update(title, addurl, saveurl, id) {
    var val = getSelected(id);
    if (val == '') {
        $.messager.show({
            title : title,
            msg : '请选择编辑项目',
            timeout : 2000,
            showType : 'slide'
        });
        return;
    }
    addurl += '&' + id + '=' + val;
    openaddorupwin(title, addurl, saveurl);
}
/**
 * 添加更新打開窗口
 * 
 * @param title
 * @param addurl
 * @param saveurl
 * @param id
 * @param gridname
 */
function openaddorupwin(title, addurl, saveurl) {
    $.createWin({
        title : title,
        winId : 'test',
        url : addurl,
        height : 420,
        target : 'body',
        width : 650,
        iconCls : 'icon-save',
        modal : false,
        buttons : [ {
            text : '确定',
            iconCls : 'icon-ok',
            handler : function() {
                saveDeclare(saveurl);
            }
        }, {
            text : '重置',
            iconCls : 'icon-cancel',
            handler : function() {
                $("#test").dialog("close");

            }
        } ],
        onComplete : function() {
        }
    // tools:[{iconCls:"icon-add"}],
    // toolbar:[{text:'按钮',iconCls:"icon-add"}]
    });
}

/**
 * 添加活更新打開窗口
 * 
 * @param title
 * @param addurl
 * @param saveurl
 * @param id
 * @param gridname
 */
function openwin(title, addurl, width, height) {
    $.createWin({
        title : title,
        winId : 'test',
        url : addurl,
        width : width === undefined ? 500 : width,
        height : height === undefined ? 300 : height,
        target : 'body',
        iconCls : 'icon-save',
        modal : true,
        onComplete : function() {
        }
    });
}

/**
 * 删除执行
 * 
 * @param url
 * @param index
 */
function ajaxSubmitConfirm(url, gridName, gridType, confirmMess) {
    if (confirmMess) {
        $.messager.confirm("确认", "确定要" + confirmMess + "吗?", function(r) {
            if (r) {
                ajaxSubmit(url, gridName, gridType);
            }
        });
    }
}

function ajaxSubmit(url, gridName, gridType) {
    $.ajax({
        url : url,
        dataType : 'json',
        success : function(result) {
            if (result.success) {
                reloadGrid(gridName, gridType);
                $.messager.show({
                    title : '提示',
                    msg : '操作成功!'
                });
            } else {
                $.messager.show({
                    title : '提示',
                    msg : '操作失败!'
                });
            }
        }
    });
}

/**
 * 窗口打开
 */
$(function() {
    $('body').append('<div id="myWindow" class="easyui-dialog" closed="true"></div>');
});
function openwindow(title, href, width, height, modal, minimizable, maximizable) {

    var $win;
    $win = $('#myWindow').dialog({
        title : title,
        width : width === undefined ? 600 : width,
        height : height === undefined ? 400 : height,
        top : ($(window).height() - 420) * 0.5,
        left : ($(window).width() - 350) * 0.5,

        content : '<iframe scrolling="no" frameborder="0" src="' + href + '" style="width:100%;height:98%;"></iframe>',
        // href : href === undefined ? null : href,
        modal : modal === undefined ? true : modal,
        minimizable : minimizable === undefined ? false : minimizable,
        maximizable : maximizable === undefined ? false : maximizable,
        shadow : true,
        cache : false,
        closed : false,
        collapsible : false,
        draggable : true,
        resizable : true,
        loadingMessage : '正在加载数据，请稍等片刻......',
        buttons : [ {
            text : '关闭',
            iconCls : "icon-add",
            handler : function() {
                $("#myWindow").dialog("close");
            }
        } ]
    });
}
/**
 * 模板页面ajax提交
 * 
 * @param url
 * @param gridname
 */
function ajaxdoSub(url, formname) {
    $('#' + formname).form('submit', {
        url : url,
        onSubmit : function() {
            $("#infocontent").val($('#content').html());
        },
        success : function(r) {
            $.messager.show({
                title : '提示消息',
                msg : '保存成功',
                timeout : 2000,
                showType : 'slide'
            });
        }
    });
}
/**
 * ajax提交FORM
 * 
 * @param url
 * @param gridname
 */
function ajaxdoForm(url, formname) {
    $('#' + formname).form('submit', {
        url : url,
        onSubmit : function() {
        },
        success : function(r) {
            $.messager.show({
                title : '提示消息',
                msg : '保存成功',
                timeout : 2000,
                showType : 'slide'
            });
        }
    });
}

/**
 * * 导出功能
 */
function export_Data(grid, action, param) {
    if (!$(grid))
        return;
    var total = $(grid).datagrid('getData').total;
    if (total == 0) {
        $.messager.alert('提示', '请先查询需要导出的数据!');
        return;
    } else if (total > 50000) {
        $.messager.alert('警告', '导出数据量过大(现上限50000条)');
        return;
    }
    if (!Wo.ajax.ready) {
        Wo.ajax.init();
    }
    Wo.get(action, param + '&ddddddd=' + new Date().getTime(), function(uuid) {
        var reg = new RegExp("\"", "g")
        uuid = uuid.replace(reg, "");
        location.href = BASE_PATH + '/statistics/export.do?uuid=' + uuid + "&dddd=" + new Date().getTime();
    });
}
